package com.safi.apps.pms.model;

public class ProductCategoryModel {

    private String categoryType;

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        System.out.println("setCategoryName called");
//        if (categoryType != null) {
//            this.categoryType = categoryType.toUpperCase();
//        }

            this.categoryType = categoryType;

    }

    @Override
    public String toString() {
        return "ProductCategory{" + "categoryType=" + categoryType + '}';
    }

}
