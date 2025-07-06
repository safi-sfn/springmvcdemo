package com.safi.util.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A utility class for establishing and managing a direct MySQL JDBC connection.
 * This class is marked as a Spring Component, allowing it to be injected.
 *
 * NOTE: For production applications, it's highly recommended to use a
 * connection pool provided by Spring Boot (e.g., HikariCP, which is default
 * with spring-boot-starter-data-jpa) rather than managing direct connections
 * like this. This example is for demonstration purposes as per the user's
 * request for a "separate class for jdbc Connection".
 */
@Component
public class MySQLConnection {

    private String url = "jdbc:mysql://localhost:3306/ecommerce?useSSL=false&serverTimezone=UTC";

    private String username = "root";

    private String password = "MySQL@2025!";

    // We'll manage a single connection here for simplicity, but this is not
    // how a connection pool works. A pool manages many connections.
    private Connection connection;

    /**
     * Initializes the JDBC connection after the bean has been constructed. This
     * method attempts to establish a connection to the MySQL database.
     */
    @PostConstruct
    public void init() {
        try {
            // Load the JDBC driver (not strictly necessary with modern JDBC drivers and Spring Boot)
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Attempting to connect to MySQL database :" + username);
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Successfully connected to MySQL database!");
        } catch (Exception e) {
            // or handle this more gracefully.
            throw new RuntimeException("Could not connect to database", e);
        }
    }

    /**
     * Provides the established JDBC connection.
     *
     * @return The active JDBC Connection object.
     * @throws SQLException if the connection is null or closed.
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            // Re-establish connection if it's closed (though a pool would handle this)
            System.out.println("Connection was closed, re-establishing...");
            init(); // Re-initialize the connection
        }
        return connection;
    }

    /**
     * Closes the JDBC connection before the bean is destroyed. This ensures
     * proper resource cleanup.
     */
    @PreDestroy
    public void destroy() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing MySQL database connection: " + e.getMessage());

            }
        }
    }
}
