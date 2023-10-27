package com.onlinemarket.rest.dto.order;

import com.onlinemarket.core.model.Order;
import com.onlinemarket.core.model.Product;
import com.onlinemarket.core.model.enums.OrderStatus;
import com.onlinemarket.rest.dto.product.ProductDTO;
import com.onlinemarket.rest.dto.user.UserDTO;

import java.util.Date;
import java.util.List;

public class OrderDTO {
    private String id;
    private String customerId;
    private List<String> items;
    private OrderStatus status;
    private Date createdAt;

    public OrderDTO(Order order) {
        this.setId(order.getId());
        this.setCustomerId(order.getCustomerId());
        this.setStatus(order.getStatus());
        this.setCreatedAt(order.getCreatedAt());
        this.setItems(order.getItems());
        this.setCreatedAt(order.getCreatedAt());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
