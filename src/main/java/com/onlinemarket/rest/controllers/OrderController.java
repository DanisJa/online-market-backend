package com.onlinemarket.rest.controllers;

import com.onlinemarket.core.model.Order;
import com.onlinemarket.core.model.User;
import com.onlinemarket.core.service.OrderService;
import com.onlinemarket.rest.dto.order.OrderDTO;
import com.onlinemarket.rest.dto.order.OrderRequestDTO;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping
@RestController("/api/orders")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {this.orderService = orderService;}

    @GetMapping
    public List<OrderDTO> findAll(){return orderService.findAll();}

    @GetMapping("/{id}")
    public OrderDTO findById(String id){
        return orderService.findById(id);
    }

    @GetMapping("/byCustomer")
    public List<OrderDTO> findByCustomer(@RequestBody User customer){
        return orderService.findByCustomer(customer);
    }

    @GetMapping("/bySeller")
    public List<OrderDTO> findBySeller(@RequestBody User seller){
        return orderService.findBySeller(seller);
    }

    @PostMapping
    public OrderDTO addOrder(@RequestBody OrderRequestDTO payload){
        return orderService.addOrder(payload);
    }

    @PutMapping("/{id}")
    public OrderDTO updateOrder(@RequestBody OrderRequestDTO payload, @PathVariable String id){
        return orderService.updateOrder(id, payload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable String id){
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
