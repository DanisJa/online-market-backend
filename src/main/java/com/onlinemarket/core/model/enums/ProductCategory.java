package com.onlinemarket.core.model.enums;

public enum ProductCategory {
    ELECTRONICS("Electronics"),
    CLOTHING("Clothing"),
    HOME_AND_FURNITURE("Home & Furniture"),
    BOOKS("Books"),
    SPORTS("Sports & Outdoors"),
    BEAUTY("Beauty & Personal Care"),
    TOYS("Toys & Games"),
    AUTOMOTIVE("Automotive"),
    FOOD("Food & Groceries"),
    OTHER("Other");

    private final String displayName;

    ProductCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}