package com.onlinemarket.core.service;

import com.onlinemarket.core.exceptions.repository.ResourceNotFoundException;
import com.onlinemarket.core.model.Order;
import com.onlinemarket.core.repo.OrderRepo;
import com.onlinemarket.rest.dto.order.OrderDTO;
import com.onlinemarket.rest.dto.order.OrderDetailsDTO;
import com.onlinemarket.rest.dto.order.OrderRequestDTO;
import com.onlinemarket.rest.dto.product.ProductDTO;
import com.onlinemarket.rest.dto.user.UserDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final ProductService productService;
    private final UserService userService;

    public OrderService(OrderRepo orderRepo, UserService userService, ProductService productService) {
        this.orderRepo = orderRepo;
        this.userService = userService;
        this.productService = productService;
    }

    private List<OrderDetailsDTO> toOrderDetailsDTO(List<Order> orders) {
        List<OrderDetailsDTO> ordersWithDetails = new ArrayList<>();

        if(orders.size() == 0) {
            return ordersWithDetails;
        }

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

    public List<OrderDTO> findAll(){
        List<Order> orders = orderRepo.findAll();
        return orders.stream().map(OrderDTO::new).collect(toList());
    }

    public List<OrderDetailsDTO> findAllWithDetails(){
        List<Order> orders = orderRepo.findAll();
        return toOrderDetailsDTO(orders);
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
            return new ArrayList<>();
        }

        return toOrderDetailsDTO(orders);
    }

    public OrderDTO addOrder(OrderRequestDTO payload){
        // check if user exists
        try{
            UserDTO user = userService.findById(payload.getCustomerId());
        } catch(ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        }
        // check if all products exist
        for(int i = 0; i < payload.getItems().size(); i++){
            try {
                ProductDTO product = productService.findById(payload.getItems().get(i));
            } catch(ResourceNotFoundException e){
                throw new ResourceNotFoundException(e.getMessage());
            }
        }
        Order order = orderRepo.save(payload.toEntity());
        return new OrderDTO(order);
    }

    public OrderDTO updateOrder(String id, OrderRequestDTO payload){
        Optional<Order> order = orderRepo.findOrderById(id);
        if(order.isEmpty()){
            throw new ResourceNotFoundException("Order with given ID doesn't exist");
        }
        //check if all products exist
        for(int i = 0; i < payload.getItems().size(); i++){
            try {
                productService.findById(payload.getItems().get(i));
            } catch(ResourceNotFoundException e){
                throw new ResourceNotFoundException(e.getMessage());
            }
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
