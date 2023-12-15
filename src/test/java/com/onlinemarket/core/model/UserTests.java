package com.onlinemarket.core.model;

import com.onlinemarket.core.model.enums.UserType;
import com.onlinemarket.rest.dto.user.UserRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
public class UserTests {
    @Test
    void shouldCreateNewUserThroughDTO() {
        UserRequestDTO newUser = new UserRequestDTO();
        newUser.setPassword("Test123");
        newUser.setEmail("test@test.com");
        newUser.setUsername("Prodavac");
        newUser.setUserType(UserType.USER);

        User user = newUser.toEntity();

        assertEquals("should have the same usertype", UserType.USER, user.getUserType());
        assertEquals("should have the same email", "test@test.com", user.getEmail());
        assertEquals("should have the same username", "Prodavac", user.getUsername());
        assertEquals("should have the same password", "Test123", user.getPassword());
    }
}
