package com.onlinemarket.rest.dto.product;

import com.onlinemarket.core.model.Product;

public class ProductRequestDTO {
    private String name, description, category;
    private double price;

    public ProductRequestDTO() {}
    public ProductRequestDTO(Product product) {
        this.name = product.getName();
        this.category = product.getCategory();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
