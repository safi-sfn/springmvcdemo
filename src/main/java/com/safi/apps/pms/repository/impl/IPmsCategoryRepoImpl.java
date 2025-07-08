package com.safi.apps.pms.repository.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.safi.util.MySqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.safi.apps.pms.repository.IPmsCategoryRepo;
import java.util.List;

@Repository
public class IPmsCategoryRepoImpl implements IPmsCategoryRepo {

    private final MySqlUtil sqlUtil;

    @Autowired
    public IPmsCategoryRepoImpl(MySqlUtil sqlUtil) {
        this.sqlUtil = sqlUtil;
    }

    @Override
    public int addCategory(String categoryType) {

        String sqlQuery = "INSERT INTO product_category (category_name) VALUES (:categoryType)";
        Map<String, Object> param = new HashMap<>();
        param.put("categoryType", categoryType);

        try {
            int insertedResult = sqlUtil.executeDml(sqlQuery, param);
            System.out.println("affectedRows :" + insertedResult);

            return insertedResult;

        } catch (SQLException e) {
            System.err.println("Error adding Category '" + categoryType + "': " + e.getMessage());
            return 0;
        }

    }

    @Override
    public List<Map<String, Object>> getCategoryData() {

        String sqlQuery = "SELECT category_id AS ID , category_name AS NAME FROM ecommerce.product_category";
        Map<String, Object> param = new HashMap<>();
        try {

            List<Map<String, Object>> resultData = sqlUtil.executeQuery(sqlQuery, param);
            System.out.println("IPmsCategoryRepoImpl -> getCategoryData resultData : "+resultData);
            return resultData;

        } catch (SQLException e) {
            System.err.println("IPmsCategoryRepoImpl -> getCategoryData exception : " + e.getMessage());
            return null;
        }

    }

}
