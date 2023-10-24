package com.onlinemarket.rest.dto.order;

import com.onlinemarket.core.model.Order;
import com.onlinemarket.core.model.Product;
import com.onlinemarket.rest.dto.product.ProductDTO;
import com.onlinemarket.rest.dto.user.UserDTO;

import java.util.Date;
import java.util.List;

public class OrderDTO {
    private String id;
    private UserDTO customer;
    private List<ProductDTO> items;
    private String status;
    private Date createdAt;

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.customer = new UserDTO(order.getCustomer());
        this.status = status;
        this.createdAt = createdAt;
        for(Product product : order.getItems()){
            this.items.add(new ProductDTO(product));
        }
    }
}
