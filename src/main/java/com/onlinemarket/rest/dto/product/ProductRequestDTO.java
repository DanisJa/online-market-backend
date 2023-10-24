package com.onlinemarket.rest.dto.product;

import com.onlinemarket.core.model.Product;
import com.onlinemarket.core.model.enums.ProductCategory;

import java.util.Date;

public class ProductRequestDTO {
    private String name, description;
    private ProductCategory category;
    private double price;

    public ProductRequestDTO() {}
    public ProductRequestDTO(Product product) {
        this.name = product.getName();
        this.category = product.getCategory();
        this.description = product.getDescription();
        this.price = product.getPrice();
    }

    public Product toEntity(){
        Product product = new Product();
        product.setCategory(this.category);
        product.setDescription(this.description);
        product.setName(this.name);
        product.setCreatedAt(new Date());
        product.setPrice(this.price);
        return product;
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

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
