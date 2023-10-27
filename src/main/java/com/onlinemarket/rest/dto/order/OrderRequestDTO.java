package com.onlinemarket.rest.dto.order;

import com.onlinemarket.core.model.Order;
import com.onlinemarket.core.model.Product;
import com.onlinemarket.core.model.User;
import com.onlinemarket.core.model.enums.OrderStatus;
import com.onlinemarket.rest.dto.product.ProductDTO;
import com.onlinemarket.rest.dto.user.UserDTO;

import java.util.Date;
import java.util.List;

public class OrderRequestDTO {
    private String customerId;
    private List<String> items;
    private OrderStatus status;
    private Date createdAt;

    public OrderRequestDTO() {}
    public OrderRequestDTO(String customerId, List<String> items, OrderStatus status){
        this.customerId = customerId;
        this.items = items;
        this.status = status;
    }

    public Order toEntity(){
        Order order = new Order();
        order.setCreatedAt(new Date());
        order.setCustomerId(this.customerId);
        order.setStatus(this.status);
        order.setItems(this.items);
        return order;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customer) {
        this.customerId = customer;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
