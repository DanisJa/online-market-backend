package com.onlinemarket.rest.controllers;

import com.onlinemarket.core.exceptions.repository.ResourceNotFoundException;
import com.onlinemarket.core.service.OrderService;
import com.onlinemarket.core.service.auth.AuthService;
import com.onlinemarket.core.service.auth.JwtService;
import com.onlinemarket.core.service.auth.UserAuthService;
import com.onlinemarket.rest.config.SecurityConfig;
import com.onlinemarket.rest.dto.user.UserLoginDTO;
import com.onlinemarket.rest.dto.user.UserLoginRequestDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(OrderControllerTests.class)
@Import(SecurityConfig.class)
public class OrderControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthService authService;

    @MockBean
    OrderService orderService;

    @MockBean
    JwtService jwtService;

    @MockBean
    AuthenticationProvider authenticationProvider;

    @MockBean
    UserAuthService userAuthService;

    @Test
    void shouldReturnNotFoundForNonExistentId() throws Exception{
        String id = "non-existent-id";

        Mockito.when(authService.signIn(any(UserLoginRequestDTO.class))).thenReturn(new UserLoginDTO("mock-jwt"));

        Mockito.when(orderService.findById(any(String.class))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login"));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/orders/" + id)
                        .header("Authorization", "Bearer " + "mock-jwt"))
                .andExpect(status().isNotFound());
    }
}
