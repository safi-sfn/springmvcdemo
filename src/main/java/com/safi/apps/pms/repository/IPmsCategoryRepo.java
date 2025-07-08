package com.safi.apps.pms.repository;

import java.util.List;
import java.util.Map;

public interface IPmsCategoryRepo {

    public int addCategory(String categoryType);
    
    public List<Map<String,Object>> getCategoryData();

}
