package com.onlinemarket.rest.controllers;

import com.jayway.jsonpath.JsonPath;
import com.onlinemarket.core.model.enums.UserType;
import com.onlinemarket.core.service.UserService;
import com.onlinemarket.core.service.auth.JwtService;
import com.onlinemarket.core.service.auth.UserAuthService;
import com.onlinemarket.rest.config.SecurityConfig;
import com.onlinemarket.rest.dto.user.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

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

    @Test
    void shouldReturnAllUsers() throws Exception {
        UserDTO user1 = new UserDTO();
        user1.setEmail("test@test.test");
        user1.setUsername("test");
        user1.setUserType(UserType.USER);

        UserDTO user2 = new UserDTO();
        user2.setEmail("test2.test.test");
        user2.setUsername("test2");
        user2.setUserType(UserType.USER);

        Mockito.when(userService.findAll()).thenReturn(List.of(user1, user2));

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn();

        String response = result.getResponse().getContentAsString();
        Assertions.assertNotEquals(1, (Integer) JsonPath.read(response, "$.length()"));
        Assertions.assertEquals(user1.getEmail(), JsonPath.read(response, "$.[0].email"));
        Assertions.assertEquals(user1.getUsername(), JsonPath.read(response, "$.[0].username"));
        Assertions.assertEquals("USER", JsonPath.read(response, "$.[0].userType"));
        Assertions.assertEquals(user2.getUsername(), JsonPath.read(response, "$.[1].username"));
    }
}
