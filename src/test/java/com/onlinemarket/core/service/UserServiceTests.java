package com.onlinemarket.core.service;

import com.onlinemarket.core.model.User;
import com.onlinemarket.core.model.enums.UserType;
import com.onlinemarket.core.repo.UserRepo;
import com.onlinemarket.rest.dto.user.UserDTO;
import com.onlinemarket.rest.dto.user.UserRequestDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@AutoConfigureMockMvc
@SpringBootTest
public class UserServiceTests {
    @MockBean
    UserRepo userRepo;

    @Autowired
    UserService userService;

    @Test
    public void shouldReturnUserWhenCreated() {
        User user = new User();
        user.setUserType(UserType.USER);
        user.setEmail("mock@mail.com");
        user.setUsername("test");
        user.setPassword("Test123");

        Mockito.when(userRepo.save(ArgumentMatchers.any(User.class))).thenReturn(user);

        UserDTO newUser = userService.addUser(new UserRequestDTO(user));
        Assertions.assertThat(user.getUsername()).isEqualTo(newUser.getUsername());
        Assertions.assertThat(user.getEmail()).isEqualTo(newUser.getEmail());
        Assertions.assertThat(user.getUserType()).isEqualTo(newUser.getUserType());
        System.out.println(newUser.getId());
    }

    @Test
    public void shouldReturnUserById() {
        User user = new User();
        user.setId("someId");
        user.setUsername("test");
        user.setEmail("tester@gmail.com");
        user.setPassword("test123");

        Mockito.when(userRepo.findById("someId")).thenReturn(Optional.of(user));

        UserDTO foundUser = userService.findById("someId");
        Assertions.assertThat(foundUser.getId()).isEqualTo(user.getId());
        Assertions.assertThat(foundUser.getUserType()).isEqualTo(user.getUserType());
        Assertions.assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
        Assertions.assertThat(foundUser.getUsername()).isEqualTo(user.getUsername());
    }
}
