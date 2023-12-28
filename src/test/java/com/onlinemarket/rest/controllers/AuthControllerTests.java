package com.onlinemarket.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onlinemarket.core.exceptions.auth.UserAlreadyExistsException;
import com.onlinemarket.core.model.enums.UserType;
import com.onlinemarket.core.service.UserService;
import com.onlinemarket.core.service.auth.AuthService;
import com.onlinemarket.core.service.auth.JwtService;
import com.onlinemarket.core.service.auth.UserAuthService;
import com.onlinemarket.rest.config.SecurityConfig;
import com.onlinemarket.rest.dto.user.UserLoginRequestDTO;
import com.onlinemarket.rest.dto.user.UserRequestDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
public class AuthControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserAuthService userAuthService;
    @MockBean
    JwtService jwtService;

    @MockBean
    UserService userService;

    @MockBean
    AuthService authService;


    @MockBean
    AuthenticationProvider authenticationProvider;

    @Test
    void shouldReturnConflictForRegistrationAttemptWithExistingMail() throws Exception {
        UserRequestDTO request = new UserRequestDTO();
        request.setEmail("test@test.test");
        request.setPassword("Test123");
        request.setUsername("Username");
        request.setUserType(UserType.USER);

        Mockito.when(authService.signUp(any(UserRequestDTO.class))).thenThrow(UserAlreadyExistsException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturnBadRequestForInvalidEmail() throws Exception {
        UserRequestDTO request = new UserRequestDTO();
        request.setEmail("danisjusic@icloud.com");
        request.setPassword("Test123");
        request.setUsername("");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnUnauthorizedForBadPassword() throws Exception {
        UserLoginRequestDTO request = new UserLoginRequestDTO("test23@test.test", "Test123");

        Mockito.when(authService.signIn(any(UserLoginRequestDTO.class))).thenThrow(BadCredentialsException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isUnauthorized());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
