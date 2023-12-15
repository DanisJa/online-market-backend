package com.onlinemarket.core.service;

import com.onlinemarket.core.exceptions.ApiExceptions.ConflictException;
import com.onlinemarket.core.exceptions.auth.UserAlreadyExistsException;
import com.onlinemarket.core.exceptions.general.BadRequestException;
import com.onlinemarket.core.exceptions.repository.ResourceNotFoundException;
import com.onlinemarket.core.model.Order;
import com.onlinemarket.core.model.User;
import com.onlinemarket.core.repo.OrderRepo;
import com.onlinemarket.core.repo.UserRepo;
import com.onlinemarket.rest.dto.user.UserDTO;
import com.onlinemarket.rest.dto.user.UserRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class UserService {
    private final UserRepo userRepo;

    private final OrderRepo orderRepo;

    public UserService(UserRepo userRepo, OrderRepo orderRepo) {
        this.userRepo = userRepo;
        this.orderRepo = orderRepo;
    }

    public List<UserDTO> findAll() {
        List<User> users = userRepo.findAll();
        return users.stream().map(UserDTO::new).collect(toList());
    }

    public UserDTO findById(String id) {
        Optional<User> userObj = userRepo.findById(id);
        if(userObj.isEmpty()){
            throw new ResourceNotFoundException("User with given ID doesn't exist.");
        }
        return new UserDTO(userObj.get());
    }

    public UserDTO addUser (UserRequestDTO payload) {
        //Check if another user with same mail or username exists
        Optional<User> userWithPayloadMail = userRepo.findUserByEmail(payload.getEmail());
        Optional<User> userWithPayloadUsername = userRepo.findUserByUsername(payload.getUsername());
        if(userWithPayloadMail.isEmpty() || userWithPayloadUsername.isEmpty()){
            User user = userRepo.save(payload.toEntity());
            return new UserDTO(user);
        }
        throw new UserAlreadyExistsException("User with given email or username already exists.");
    }

    public UserDTO updateUser (String id, UserRequestDTO payload) {
        Optional<User> user = userRepo.findUserById(id);

        if(!(payload instanceof UserRequestDTO)){
            throw new BadRequestException("Request body not matching the expected type");
        }

        if(user.isEmpty()){
            throw new ResourceNotFoundException("User with given ID doesn't exist.");
        }
        User updatedUser = payload.toEntity();
        updatedUser.setId(user.get().getId());
        updatedUser = userRepo.save(updatedUser);
        return new UserDTO(updatedUser);
    }

    public void deleteUser(String id) {
        List<Order> ordersByUser = orderRepo.findOrdersByCustomerId(id);

        if (!ordersByUser.isEmpty()) {
            throw new ConflictException("User with given ID has associated uncompleted orders.");
        }

        Optional<User> user = userRepo.findUserById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User with given ID doesn't exist.");
        }

        userRepo.delete(user.get());
    }

}
