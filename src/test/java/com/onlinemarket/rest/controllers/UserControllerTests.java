package com.onlinemarket.rest.controllers;

import com.onlinemarket.core.service.UserService;
import com.onlinemarket.core.service.auth.JwtService;
import com.onlinemarket.core.service.auth.UserAuthService;
import com.onlinemarket.rest.config.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
public class UserControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserAuthService authService;

    @MockBean
    UserService userService;

    @MockBean
    JwtService jwtService;

    @MockBean
    AuthenticationProvider authenticationProvider;
}
