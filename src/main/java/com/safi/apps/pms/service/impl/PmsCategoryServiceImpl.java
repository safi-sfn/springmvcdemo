package com.safi.apps.pms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safi.apps.pms.repository.IPmsCategoryRepo;
import com.safi.apps.pms.service.IPmsCategoryService;
import java.util.List;
import java.util.Map;

@Service
public class PmsCategoryServiceImpl implements IPmsCategoryService {

	@Autowired
	IPmsCategoryRepo repository;
	
	@Override
	public boolean addCategory(String categoryType) {
		int res = repository.addCategory(categoryType);
		if(res==1) {
			return true;
		}
		return false;
	}

    @Override
    public List<Map<String, Object>> getCategoryData() {
        
       List<Map<String,Object>> dataList =  repository.getCategoryData();
       
        System.out.println("PmsCategoryServiceImpl -> getCategoryData dataList :"+dataList);
       
       return dataList;
       
    }

}
