package com.safi.apps.pms.repository.impl;

import com.safi.util.MySqlUtil;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.safi.apps.pms.repository.IPmsBrandRepo;

/**
 *
 * @author safi
 */
@Repository
public class PmsBrandRepoImpl implements IPmsBrandRepo {

    private final MySqlUtil sqlUtil;

    /**
     * Injects the MySqlUtil bean.
     * @param jdbcTemplateUtil The MySqlUtil instance.
     */
    @Autowired
    public PmsBrandRepoImpl(MySqlUtil sqlUtil) {
        this.sqlUtil = sqlUtil;
    }
    
    @Override
    public int addBrand(String brandName) {
        
        String sql = "INSERT INTO product_brand (brand_name) VALUES (:brandName)"; 
        
        Map<String, Object> params = new HashMap<>();
        params.put("brandName", brandName); // Map the parameter name to its value

        try {
            int affectedRows = sqlUtil.executeDml(sql, params);
            System.out.println("affectedRows :" + affectedRows);
            if (affectedRows > 0) {
                return 1;
            }
            return 0;

        } catch (SQLException e) {
            System.err.println("Error adding brand '" + brandName + "': " + e.getMessage());
            // In a real application, you might log this and re-throw a custom exception
            return 0;
        }
    }
    
    // Example of a new method using the utility (e.g., to get a brand by name)
    public Map<String, Object> getBrandByName(String brandName) {
        String sql = "SELECT brand_id, brand_name FROM product_brand WHERE brand_name = :brandName";
        Map<String, Object> params = new HashMap<>();
        params.put("brandName", brandName);

        try {
            List<Map<String, Object>> result = sqlUtil.executeQuery(sql, params);
            if (!result.isEmpty()) {
                return result.get(0); // Return the first matching brand
            }
            return null; // No brand found
        } catch (SQLException e) {
            System.err.println("Error getting brand by name '" + brandName + "': " + e.getMessage());
            // In a real application, you might log this and re-throw a custom exception
            return null;
        }
    }

    // Example of an update method
    public int updateBrandName(int brandId, String newBrandName) {
        String sql = "UPDATE product_brand SET brand_name = :newBrandName WHERE brand_id = :brandId";
        Map<String, Object> params = new HashMap<>();
        params.put("newBrandName", newBrandName);
        params.put("brandId", brandId);

        try {
            return sqlUtil.executeDml(sql, params);
        } catch (SQLException e) {
            System.err.println("Error updating brand " + brandId + ": " + e.getMessage());
            return 0;
        }
    }
}