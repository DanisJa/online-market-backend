package com.onlinemarket.rest.controllers;

import com.onlinemarket.core.service.OrderService;
import com.onlinemarket.rest.dto.order.OrderDTO;
import com.onlinemarket.rest.dto.order.OrderDetailsDTO;
import com.onlinemarket.rest.dto.order.OrderRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("api/orders")
@RestController
@SecurityRequirement(name = "jwt-auth")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {this.orderService = orderService;}

    @GetMapping
    public List<OrderDetailsDTO> findAll(){return orderService.findAllWithDetails();}

    @GetMapping("/{id}")
    public OrderDTO findById(String id){
        return orderService.findById(id);
    }

    @GetMapping("/byCustomer")
    public List<OrderDetailsDTO> findByCustomer(@RequestParam String customerId){
        return orderService.findByCustomerId(customerId);
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
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
