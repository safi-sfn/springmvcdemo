/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.safi.apps.pms.service.impl;
import com.safi.apps.pms.dao.IPmsManager;
import com.safi.apps.pms.service.IPmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author safi
 */
@Service
public class PmsServiceImpl implements IPmsService {

     @Autowired
    IPmsManager manager;
     
    
    @Override
    public boolean addBrand(String brandName) {
       int res = manager.addBrand(brandName);
       if(res==1){
           return true;
       }
        return false;
    }
    
   
    
   
}
