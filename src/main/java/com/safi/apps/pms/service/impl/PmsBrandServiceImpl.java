/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.safi.apps.pms.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.safi.apps.pms.repository.IPmsBrandRepo;
import com.safi.apps.pms.service.IPmsBrandService;
/**
 *
 * @author safi
 */
@Service
public class PmsBrandServiceImpl implements IPmsBrandService {

     @Autowired
    IPmsBrandRepo manager;
     
    
    @Override
    public boolean addBrand(String brandName) {
       int res = manager.addBrand(brandName);
       if(res==1){
           return true;
       }
        return false;
    }
    
   
    
   
}
