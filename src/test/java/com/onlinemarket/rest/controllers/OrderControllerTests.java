package com.onlinemarket.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinemarket.core.exceptions.repository.ResourceNotFoundException;
import com.onlinemarket.core.model.Order;
import com.onlinemarket.core.model.enums.OrderStatus;
import com.onlinemarket.core.repo.OrderRepo;
import com.onlinemarket.core.service.OrderService;
import com.onlinemarket.rest.dto.order.OrderDTO;
import com.onlinemarket.rest.dto.order.OrderRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTests {
    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private OrderRepo orderRepo;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void shouldReturnEmptyArrayWhenFindAllCalled() throws Exception {
        // Arrange
        when(orderService.findAllWithDetails()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
    }

    @Test
    void shouldReturnOrderById() throws Exception {
        // Arrange
        OrderDTO order = new OrderDTO(new Order());
        String orderId = "1";
        when(orderService.findById(orderId)).thenReturn(order);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/{id}", orderId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(order.getId()));
    }

    @Test
    void shouldReturnNotFoundForNonExistentOrderById() throws Exception {
        // Arrange
        String orderId = "non-existent-id";
        when(orderService.findById(orderId)).thenThrow(ResourceNotFoundException.class);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/{id}", orderId))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(false));

        verify(orderService, times(1)).findById(orderId);
    }
    @Test
    void shouldReturnEmptyArrayForNoOrdersByCustomer() throws Exception{
        String customerId = "non-existent-id";
        when(orderService.findByCustomerId(customerId)).thenReturn(List.of());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/byCustomer/{customerId}", customerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));
    }

    @Test
    void shouldReturnNotFoundForUpdatingNonExistentOrder() throws Exception{
        String orderId = "non-existent-id";
        String customerId = "some-customer-id";
        OrderRequestDTO updatedOrder = new OrderRequestDTO(customerId, List.of("some-product-id", "some-product-id-2"), OrderStatus.PENDING);

        when(orderService.updateOrder(any(String.class), any(OrderRequestDTO.class)))
                .thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedOrder)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundForDeletingNonExistentOrder() throws Exception{
        String orderId = "non-existent-id";

        doThrow(ResourceNotFoundException.class).when(orderService).deleteOrder(any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/orders/{id}", orderId)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
