package com.onlinemarket.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinemarket.core.exceptions.ApiExceptions.ConflictException;
import com.onlinemarket.core.exceptions.repository.ResourceNotFoundException;
import com.onlinemarket.core.service.UserService;
import com.onlinemarket.rest.dto.user.UserRequestDTO;
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
public class UserControllerTests {
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    String userId = "non-existent-id";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void shouldReturnNotFoundForNonExistentId() throws Exception{
        when(userService.findById(any(String.class))).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundForUpdatingWithNonExistentId() throws Exception{
        when(userService.updateUser(any(String.class), any(UserRequestDTO.class))).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UserRequestDTO()))
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundForDeletingWithNonExistentId() throws Exception{
        doThrow(ResourceNotFoundException.class).when(userService).deleteUser(any(String.class));
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldReturnBadRequestForInvalidRequestBodyOnUpdate() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("this is a bad request")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldReturnConflictForUserWithAssociatedOrders() throws Exception{
        doThrow(ConflictException.class).when(userService).deleteUser(userId);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }
}
