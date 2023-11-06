package com.onlinemarket.rest.dto.order;

import com.onlinemarket.core.model.Order;
import com.onlinemarket.core.model.enums.OrderStatus;
import com.onlinemarket.rest.dto.product.ProductDTO;
import com.onlinemarket.rest.dto.user.UserDTO;

import java.util.Date;
import java.util.List;

public class OrderDetailsDTO {
    private String id;
    private OrderStatus status;
    private UserDTO customer;
    private List<ProductDTO> items;
    private Date createdAt;

    public OrderDetailsDTO(Order order, UserDTO customer, List<ProductDTO> items){
        this.id = order.getId();
        this.status = order.getStatus();
        this.createdAt = order.getCreatedAt();
        this.customer = customer;
        this.items = items;
    }

    public UserDTO getCustomer() {
        return customer;
    }

    public void setCustomer(UserDTO customer) {
        this.customer = customer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<ProductDTO> getItems() {
        return items;
    }

    public void setItems(List<ProductDTO> items) {
        this.items = items;
    }
}
