package com.onlinemarket.rest.controllers;

import com.onlinemarket.core.exceptions.repository.ResourceNotFoundException;
import com.onlinemarket.core.service.OrderService;
import com.onlinemarket.rest.dto.order.OrderDTO;
import com.onlinemarket.rest.dto.order.OrderDetailsDTO;
import com.onlinemarket.rest.dto.order.OrderRequestDTO;
import com.onlinemarket.rest.dto.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "localhost:5173")
@RestController
@RequestMapping("/api/orders")
@SecurityRequirement(name = "jwt-auth")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDetailsDTO>>> findAll(){
        try {
            return ResponseEntity
                    .ok(new ApiResponse<>(true, orderService.findAllWithDetails()));
        } catch(Exception e){
            return ResponseEntity
                    .status(500)
                    .body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDetailsDTO>> findById(@PathVariable String id){
        try {
            return ResponseEntity
                    .ok(new ApiResponse<>(true, orderService.findById(id)));
        } catch(ResourceNotFoundException error){
            return ResponseEntity
                    .status(404).body(new ApiResponse<>(false, error.getMessage()));
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/byCustomer/{customerId}")
    public ResponseEntity<ApiResponse<List<OrderDetailsDTO>>> findByCustomer(@PathVariable String customerId){
        try {
            return ResponseEntity
                    .ok(new ApiResponse<>(true, orderService.findByCustomerId(customerId)));
        } catch (ResourceNotFoundException error){
            System.out.println(error.getMessage());
            return ResponseEntity.status(404).body(new ApiResponse<>(false, error.getMessage()));
        } catch (Exception error){
            System.out.println(error.getMessage());
            return ResponseEntity.status(500).body(new ApiResponse<>(false, error.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDTO>> addOrder(@RequestBody OrderRequestDTO payload){
        try {
            return ResponseEntity
                    .ok(new ApiResponse<>(true, orderService.addOrder(payload)));
        } catch(ResourceNotFoundException error){
            return ResponseEntity
                    .status(404).body(new ApiResponse<>(false, error.getMessage()));
        } catch(Exception error){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, error.getMessage()));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDTO>> updateOrder(@RequestBody OrderRequestDTO payload, @PathVariable String id){
        try {
            return ResponseEntity
                    .ok(new ApiResponse<>(true, orderService.updateOrder(id, payload)));
        } catch(ResourceNotFoundException error){
            return ResponseEntity
                    .status(404).body(new ApiResponse<>(false, error.getMessage()));
        } catch(Exception error){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, error.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteOrder(@PathVariable String id){
        try {
            orderService.deleteOrder(id);
            return ResponseEntity
                    .status(200)
                    .body(new ApiResponse<>(true, "Successfully deleted the order"));
        } catch (ResourceNotFoundException error){
            return ResponseEntity
                    .status(404)
                    .body(new ApiResponse<>(false, error.getMessage()));
        }
    }
}
