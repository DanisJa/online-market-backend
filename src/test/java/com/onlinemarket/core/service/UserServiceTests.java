package com.onlinemarket.core.service;

import com.onlinemarket.core.model.User;
import com.onlinemarket.core.model.enums.UserType;
import com.onlinemarket.core.repo.OrderRepo;
import com.onlinemarket.core.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@AutoConfigureMockMvc
@SpringBootTest
public class UserServiceTests {
    @Mock
    UserRepo userRepo;

    @Mock
    OrderRepo orderRepo;

    @Autowired
    UserService userService;

    User userExample = new User();

    @BeforeEach
    void setUp(){
        userExample.setUserType(UserType.USER);
        userExample.setEmail("test@test.test");
        userExample.setId("existingUserId");
        userExample.setCreatedAt(new Date());
        userExample.setPassword("Test123!");
    }

//    @Test
//    void shouldDeleteUserWithNoAssociatedOrders() {
//        // Arrange
//        String userId = userExample.getId();
//        Optional<User> user = Optional.of(userExample);
//        when(userRepo.findUserById(any(String.class))).thenReturn(user);
//        when(orderRepo.findOrdersByCustomerId(any(String.class))).thenReturn(new ArrayList<>());
//
//        // Act
//        userService.deleteUser(userId);
//
//        // Assert
//        verify(userRepo, times(1)).delete(user.get());
//    }
//
//    @Test
//    void shouldThrowConflictExceptionForUserWithAssociatedOrders() {
//        // Arrange
//        String userId = userExample.getId();
//        when(userRepo.findUserById(any(String.class))).thenReturn(Optional.of(userExample));
//
//        List<Order> associatedOrders = new ArrayList<>();
//        associatedOrders.add(new Order());
//        associatedOrders.add(new Order());
//
//        when(orderRepo.findOrdersByCustomerId(userId)).thenReturn(associatedOrders);
//
//        // Act & Assert
//        assertThrows(ConflictException.class, () -> userService.deleteUser(userId));
//        verify(userRepo, never()).delete(any(User.class));
//    }
}
