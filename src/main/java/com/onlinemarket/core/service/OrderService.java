package com.onlinemarket.core.service;

import com.onlinemarket.core.exceptions.repository.ResourceNotFoundException;
import com.onlinemarket.core.model.Order;
import com.onlinemarket.core.model.User;
import com.onlinemarket.rest.dto.order.OrderDTO;
import com.onlinemarket.rest.dto.order.OrderRequestDTO;
import org.springframework.stereotype.Service;
import com.onlinemarket.core.repo.OrderRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;


@Service
public class OrderService {
    private OrderRepo orderRepo;

    public OrderService(OrderRepo orderRepo) {this.orderRepo = orderRepo;}

    public List<OrderDTO> findAll(){
        List<Order> orders = orderRepo.findAll();
        return orders.stream().map(order -> new OrderDTO(order)).collect(toList());
    }

    public OrderDTO findById(String id){
        Optional<Order> order = orderRepo.findOrderById(id);
        if(order.isEmpty()){
            throw new ResourceNotFoundException("Order with given ID does not exist.");
        }
        return new OrderDTO(order.get());
    }

    public List<OrderDTO> findByCustomer(User customer){
        List<Order> orderList = orderRepo.findOrdersByCustomer(customer);

        if(orderList.isEmpty()){
            throw new ResourceNotFoundException("Given customer does not have any orders");
        }

        List<OrderDTO> orders = new ArrayList<>();
        orderList.stream().map(order -> orders.add(new OrderDTO(order))).collect(toList());
        return orders;
    }

    public List<OrderDTO> findBySeller(User seller){
        List<Order> orderList = orderRepo.findOrdersBySeller(seller);

        if(orderList.isEmpty()){
            throw new ResourceNotFoundException("Given seller does not have any orders");
        }

        List<OrderDTO> orders = new ArrayList<>();
        orderList.stream().map(order -> orders.add(new OrderDTO(order))).collect(toList());
        return orders;
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
