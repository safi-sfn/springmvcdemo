package com.safi.apps.pms.service;

import java.util.List;
import java.util.Map;


public interface IPmsCategoryService {

    public boolean addCategory(String categoryType);
    
    public List<Map<String,Object>> getCategoryData();
}
