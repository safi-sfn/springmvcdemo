package com.safi.apps.pms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safi.apps.pms.dao.IPmsCategoryRepo;
import com.safi.apps.pms.service.IPmsCategory;

@Service
public class PmsCategoryImpl implements IPmsCategory {

	@Autowired
	IPmsCategoryRepo categoryRepo;
	
	@Override
	public boolean addCategory(String categoryType) {
		int res = categoryRepo.addCategory(categoryType);
		if(res==1) {
			return true;
		}
		return false;
	}

}
