package com.onlinemarket.core.service;

import com.onlinemarket.core.exceptions.repository.ResourceNotFoundException;
import com.onlinemarket.core.model.Order;
import com.onlinemarket.rest.dto.order.OrderDTO;
import com.onlinemarket.rest.dto.order.OrderRequestDTO;
import com.onlinemarket.rest.dto.order.OrderDetailsDTO;
import com.onlinemarket.rest.dto.product.ProductDTO;
import com.onlinemarket.rest.dto.user.UserDTO;
import org.springframework.stereotype.Service;
import com.onlinemarket.core.repo.OrderRepo;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


@Service
public class OrderService {
    private OrderRepo orderRepo;
    private ProductService productService;
    private UserService userService;

    public OrderService(OrderRepo orderRepo, UserService userService, ProductService productService) {
        this.orderRepo = orderRepo;
        this.userService = userService;
        this.productService = productService;
    }

    public List<OrderDTO> findAll(){
        List<Order> orders = orderRepo.findAll();
        return orders.stream().map(OrderDTO::new).collect(toList());
    }

    public List<OrderDetailsDTO> findAllWithDetails(){
        List<Order> orders = orderRepo.findAll();
        List<OrderDetailsDTO> ordersWithDetails = new ArrayList<>();

        for(Order order : orders){
            UserDTO customer = userService.findById(order.getCustomerId());
            List<ProductDTO> items = new ArrayList<>();
            for(String id : order.getItems()){
                ProductDTO product = productService.findById(id);
                items.add(product);
            }
            ordersWithDetails.add(new OrderDetailsDTO(order, customer, items));
        }

        return ordersWithDetails;
    }

    public OrderDTO findById(String id){
        Optional<Order> order = orderRepo.findOrderById(id);
        if(order.isEmpty()){
            throw new ResourceNotFoundException("Order with given ID does not exist.");
        }
        return new OrderDTO(order.get());
    }

    public List<OrderDetailsDTO> findByCustomerId(@RequestBody String customerId){
        List<Order> orders = orderRepo.findOrdersByCustomerId(customerId);

        if(orders.isEmpty()){
            throw new ResourceNotFoundException("Given customer does not have any orders");
        }

        List<OrderDetailsDTO> ordersWithDetails = new ArrayList<>();

        for(Order order : orders){
            UserDTO customer = userService.findById(order.getCustomerId());
            List<ProductDTO> items = new ArrayList<>();
            for(String id : order.getItems()){
                ProductDTO product = productService.findById(id);
                items.add(product);
            }
            ordersWithDetails.add(new OrderDetailsDTO(order, customer, items));
        }

        return ordersWithDetails;
    }

    public OrderDTO addOrder(OrderRequestDTO payload){
        Order order = orderRepo.save(payload.toEntity());
        return new OrderDTO(order);
    }

    public OrderDTO updateOrder(String id, OrderRequestDTO payload){
        Optional<Order> order = orderRepo.findOrderById(id);
        if(order.isEmpty()){
            throw new ResourceNotFoundException("Order with given ID doesn't exist");
        }
        Order updatedOrder = payload.toEntity();
        updatedOrder.setId(order.get().getId());
        updatedOrder = orderRepo.save(updatedOrder);
        return new OrderDTO(updatedOrder);
    }

    public void deleteOrder(String id){
        Optional<Order> order = orderRepo.findOrderById(id);
        if(order.isEmpty()){
            throw new ResourceNotFoundException("Order with given ID doesn't exist.");
        }
        orderRepo.delete(order.get());
    }
}
