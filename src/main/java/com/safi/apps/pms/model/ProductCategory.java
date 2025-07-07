package com.safi.apps.pms.model;

public class ProductCategory {

	private Integer categoryId;
	private String categoryType;
//	private String occasionType;

	public ProductCategory() {
		
	}
	public ProductCategory(Integer categoryId, String categoryType) {
		super();
		this.categoryId = categoryId;
		this.categoryType = categoryType;
//		this.occasionType = occasionType;
	}


	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

//	public String getOccasionType() {
//		return occasionType;
//	}
//
//	public void setOccasionType(String occasionType) {
//		this.occasionType = occasionType;
//	}

	@Override
	public String toString() {
		return "ProductCategory [categoryId=" + categoryId + ", categoryType=" + categoryType + ", occasionType="
				 + "]";
	}

}
