package com.onlinemarket.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinemarket.core.exceptions.repository.ResourceNotFoundException;
import com.onlinemarket.core.model.Product;
import com.onlinemarket.core.service.ProductService;
import com.onlinemarket.rest.dto.product.ProductRequestDTO;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTests {
    @InjectMocks
    ProductController productController;

    @Mock
    private ProductService productService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void shouldReturnNotFoundForNonExistentId() throws Exception{
        when(productService.findById(any(String.class))).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/{id}", "some-id"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundForUpdatingNonExistentId() throws Exception{
        when(productService.updateProduct(any(String.class), any(ProductRequestDTO.class)))
                .thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/products/{id}", "some-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ProductRequestDTO(new Product()))))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundForDeletingNonExistentProduct() throws Exception{
        doThrow(ResourceNotFoundException.class).when(productService).deleteProduct(any(String.class));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/{id}", "some-id"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
