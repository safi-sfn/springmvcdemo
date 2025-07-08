package com.safi.apps.pms.model; // Adjust package based on your structure

public class BrandModel {
    private String name; // This field will map to the "name" in your JSON

    // No-argument constructor is REQUIRED for JSON deserialization
    public BrandModel() {
    }

    public BrandModel(String name) {
        this.name = name;
    }

    // Getter and Setter are REQUIRED for JSON serialization/deserialization
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Brand{" +
               "name='" + name + '\'' +
               '}';
    }
}