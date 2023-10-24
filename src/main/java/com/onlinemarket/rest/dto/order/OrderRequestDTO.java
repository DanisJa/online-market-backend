package com.onlinemarket.rest.dto.order;

import com.onlinemarket.core.model.Order;
import com.onlinemarket.core.model.Product;
import com.onlinemarket.core.model.User;
import com.onlinemarket.core.model.enums.OrderStatus;

import java.util.Date;
import java.util.List;

public class OrderRequestDTO {
    private User customer;
    private List<Product> items;

    public OrderRequestDTO() {}
    public OrderRequestDTO(User user, List<Product> products){
        this.customer = user;
        this.items = products;
    }

    public Order toEntity(){
        Order order = new Order();
        order.setCreatedAt(new Date());
        order.setCustomer(this.customer);
        order.setStatus(OrderStatus.PENDING);
        order.setItems(this.items);
        return order;
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
