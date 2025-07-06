package com.safi.apps.pms.controller;

import com.safi.apps.pms.service.IPmsService;
import com.safi.apps.pms.model.Brand; // Import the new Brand POJO
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/pms")

public class PmsController {
    
    @Autowired
    IPmsService service;
    
    @PostMapping("/addBrand")
    public ResponseEntity<Map<String,Object>> addBrand(@RequestBody Brand brand) {

        
        System.out.println("Received status: " + brand.getName()); 
     
        boolean result = service.addBrand(brand.getName()); 
        System.out.println("Result : " + result); 
        
        Map<String,Object> respMap = new HashMap<>();
        
        respMap.put("isSucess", result);
        respMap.put("new", "Y");
        respMap.put("receivedBrandName", brand.getName()); 
        
        
        return new ResponseEntity<>(respMap, HttpStatus.CREATED);
    }
    
    
}