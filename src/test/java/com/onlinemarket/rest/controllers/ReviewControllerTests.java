package com.onlinemarket.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinemarket.core.exceptions.repository.ResourceNotFoundException;
import com.onlinemarket.core.repo.ReviewRepo;
import com.onlinemarket.core.service.ReviewService;
import com.onlinemarket.rest.dto.review.ReviewRequestDTO;
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
public class ReviewControllerTests {
    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private ReviewService reviewService;

    @Mock
    private ReviewRepo reviewRepo;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    String reviewId = "non-existent-id";
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    void shouldReturnNotFoundForNonExistentId() throws Exception {
        when(reviewService.findById(any(String.class))).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/reviews/{id}", reviewId)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundForUpdatingWithNonExistentId() throws Exception{
        when(reviewService.updateReview(any(String.class), any(ReviewRequestDTO.class))).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/reviews/{id}", reviewId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new ReviewRequestDTO())))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundForDeletingWithNonExistentId() throws Exception{
        doThrow(ResourceNotFoundException.class).when(reviewService).deleteReview(any(String.class));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/reviews/{id}", reviewId)).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}