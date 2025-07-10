package com.safi.apps.pms.controller;

import com.safi.apps.pms.model.BrandModel;
import com.safi.apps.pms.model.ProductCategoryModel;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.safi.apps.pms.service.IPmsBrandService;
import com.safi.apps.pms.service.IPmsCategoryService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/rest/pms")
public class PmsController {

    @Autowired
    IPmsBrandService brandService;

    @Autowired
    IPmsCategoryService categoryService;

    @PostMapping("/addBrand")
    public ResponseEntity<Map<String, Object>> addBrand(@RequestBody BrandModel brandModel) {

        System.out.println("Received status: " + brandModel.getName());

        boolean result = brandService.addBrand(brandModel.getName());
        System.out.println("Result : " + result);

        Map<String, Object> respMap = new HashMap<>();

        respMap.put("isSucess", result);
        respMap.put("new", "Y");
        respMap.put("receivedBrandName", brandModel.getName());

        return new ResponseEntity<>(respMap, HttpStatus.CREATED);
    }

    @PostMapping("/addCategory")
    public ResponseEntity<Map<String, Object>> addCategory(@RequestBody ProductCategoryModel categoryModelObj) {
        System.out.println("Category status: " + categoryModelObj.toString());

        Map<String, Object> respMap = new HashMap<>();
        if (categoryModelObj.getCategoryType() == null || categoryModelObj.getCategoryType().trim().isBlank()) {
            respMap.put("isSucess", false);
            respMap.put("message", "missing field :categoryType");

            return new ResponseEntity<>(respMap, HttpStatus.BAD_REQUEST);
        }

        boolean categoryResult = categoryService.addCategory(categoryModelObj.getCategoryType());
        System.out.println("Result : " + categoryResult);

        respMap.put("isSucess", categoryResult);
        respMap.put("Recieved categoryType", categoryModelObj.getCategoryType());

        return new ResponseEntity<>(respMap, HttpStatus.CREATED);

    }

    @GetMapping("/getCategory")
    public ResponseEntity<Map<String, Object>> getCategoryData() {

        System.out.println("PmsController -> getCategoryData called");
        Map<String, Object> respMap = new HashMap<>();
        List<Map<String, Object>> resultDataList = categoryService.getCategoryData();
//        Map<String,Object> resultDataMap = resultDataList.get(4);
//       System.out.println("PmsController -> getCategoryData resultDataMap" + resultDataMap);

        respMap.put("data", resultDataList);
        respMap.put("isSucess", true);

        return new ResponseEntity<>(respMap, HttpStatus.FOUND);
        
    }

}
