package com.onlinemarket.rest.dto.order;

import com.onlinemarket.core.model.Product;
import com.onlinemarket.core.model.User;

import java.util.List;

public class OrderRequestDTO {
    private User customer;
    private List<Product> items;

    public OrderRequestDTO() {}
    public OrderRequestDTO(User user, List<Product> products){
        this.customer = user;
        this.items = products;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public List<Product> getItems() {
        return items;
    }

    public void setItems(List<Product> items) {
        this.items = items;
    }
}
