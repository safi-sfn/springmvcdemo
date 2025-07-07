package com.safi.apps.pms.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.safi.apps.pms.dao.IPmsCategoryRepo;
import com.safi.util.MySqlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
		Map<String, Object> catgMap = new HashMap<>();
		catgMap.put("categoryType", categoryType);

		try {
			int affectedRows = sqlUtil.executeDml(sqlQuery, catgMap);
			System.out.println("affectedRows :" + affectedRows);
			if (affectedRows > 0) {
				return 1;
			}
			return 0;

		} catch (SQLException e) {
			System.err.println("Error adding Category '" + categoryType + "': " + e.getMessage());
			// In a real application, you might log this and re-throw a custom exception
			return 0;
		}

	}
	
	
	
	public Map<String,Object> getCategoryByType(String categoryType){
		
		String sqlQuery = "SELECT category_id , category_name FROM product_category WHERE category_name = :categoryType";
		
		Map<String,Object> catgTypeMap = new HashMap<>();
		catgTypeMap.put("categoryType", categoryType);
		
		  try {
	            List<Map<String, Object>> result = sqlUtil.executeQuery(sqlQuery, catgTypeMap);
	            if (!result.isEmpty()) {
	                return result.get(0); // Return the first matching category
	            }
	            return null; // No brand found
	        } catch (SQLException e) {
	            System.err.println("Error getting category by name '" + categoryType + "': " + e.getMessage());
	            // In a real application, you might log this and re-throw a custom exception
	            return null;
	        }
		  	

	}
	
	
	
	 public int updateCategoryType(int categoryId, String newCategoryType) {
	        String sql = "UPDATE product_category SET category_name = :newCategoryType WHERE category_id = :categoryId";
	        Map<String, Object> params = new HashMap<>();
	        params.put("newCategoryType", newCategoryType);
	        params.put("categoryId", categoryId);

	        try {
	            return sqlUtil.executeDml(sql, params);
	        } catch (SQLException e) {
	            System.err.println("Error updating category " + categoryId + ": " + e.getMessage());
	            return 0;
	        }
	    }

}
