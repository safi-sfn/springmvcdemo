/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.safi.util;

import com.safi.util.config.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component; // Use @Component or @Repository, @Component is more general for utilities

/**
 * A utility class for performing common JDBC operations with dynamic parameters.
 * Supports DML (INSERT, UPDATE, DELETE) and SELECT queries.
 */
@Component
public class MySqlUtil {

    private final MySQLConnection mySQLConnection;

    @Autowired
    public MySqlUtil(MySQLConnection mySQLConnection) {
        this.mySQLConnection = mySQLConnection;
    }

    /**
     * Executes a DML (INSERT, UPDATE, DELETE) query with dynamic parameters.
     *
     * @param sql The SQL query string with named parameters (e.g., "INSERT INTO table (col) VALUES (:paramName)").
     * @param params A map where keys are parameter names (without ':') and values are their corresponding objects. Can be null if no parameters.
     * @return The number of rows affected by the DML operation.
     * @throws SQLException If a database access error occurs.
     */
    public int executeDml(String sql, Map<String, Object> params) throws SQLException {
        PreparedQuery processedQuery = processNamedParameters(sql, params);
        
        try (Connection conn = mySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(processedQuery.getSql())) {

            setParameters(pstmt, processedQuery.getOrderedParams());
            return pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e; // Re-throw the exception for proper error handling upstream
        }
    }

    /**
     * Executes a SELECT query with dynamic parameters and returns the result as a list of maps.
     * Each map represents a row, with column names as keys and column values as values.
     *
     * @param sql The SQL query string with named parameters (e.g., "SELECT * FROM table WHERE id = :id").
     * @param params A map where keys are parameter names (without ':') and values are their corresponding objects. Can be null if no parameters.
     * @return A List of Maps, where each Map represents a row from the ResultSet.
     * @throws SQLException If a database access error occurs.
     */
    public List<Map<String, Object>> executeQuery(String sql, Map<String, Object> params) throws SQLException {
        List<Map<String, Object>> resultList = new ArrayList<>();
        PreparedQuery processedQuery = processNamedParameters(sql, params);

        try (Connection conn = mySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(processedQuery.getSql())) {

            setParameters(pstmt, processedQuery.getOrderedParams());

            try (ResultSet rs = pstmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnLabel(i); // Use getColumnLabel for aliased columns
                        Object columnValue = rs.getObject(i);
                        row.put(columnName, columnValue);
                    }
                    resultList.add(row);
                }
            }
        } catch (SQLException e) {
            throw e; // Re-throw the exception
        }
        return resultList;
    }

    /**
     * Helper method to replace named parameters with '?' and extract ordered parameters.
     *
     * @param sql The original SQL with named parameters.
     * @param params The map of parameter names to values.
     * @return A PreparedQuery object containing the modified SQL and the ordered list of parameters.
     */
    private PreparedQuery processNamedParameters(String sql, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        List<Object> orderedParams = new ArrayList<>();
        
        // Pattern to find :paramName (word characters)
        Pattern pattern = Pattern.compile(":\\w+");
        Matcher matcher = pattern.matcher(sql);
        
        int lastIndex = 0;
        while (matcher.find()) {
            sb.append(sql.substring(lastIndex, matcher.start()));
            sb.append("?"); // Replace :paramName with ?

            String paramNameWithColon = matcher.group();
            String paramName = paramNameWithColon.substring(1);

            if (params != null && params.containsKey(paramName)) {
                orderedParams.add(params.get(paramName));
            } else {
                System.err.println("Warning: Parameter '" + paramName + "' found in SQL but not in provided params map for query: " + sql);
                orderedParams.add(null);
            }
            lastIndex = matcher.end();
        }
        sb.append(sql.substring(lastIndex)); // Append remaining part of the string

        return new PreparedQuery(sb.toString(), orderedParams);
    }

    /**
     * Helper method to set parameters on a PreparedStatement.
     */
    private void setParameters(PreparedStatement pstmt, List<Object> orderedParams) throws SQLException {
        for (int i = 0; i < orderedParams.size(); i++) {
            pstmt.setObject(i + 1, orderedParams.get(i));
        }
    }

    /**
     * Internal class to hold the processed SQL and ordered parameters.
     */
    private static class PreparedQuery {
        private final String sql;
        private final List<Object> orderedParams;

        public PreparedQuery(String sql, List<Object> orderedParams) {
            this.sql = sql;
            this.orderedParams = orderedParams;
        }

        public String getSql() {
            return sql;
        }

        public List<Object> getOrderedParams() {
            return orderedParams;
        }
    }
}