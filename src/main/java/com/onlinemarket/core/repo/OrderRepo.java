package com.onlinemarket.core.repo;

import com.onlinemarket.core.model.Order;
import com.onlinemarket.core.model.User;
import com.onlinemarket.rest.dto.user.UserDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends MongoRepository<Order, String> {
    List<Order> findAll();
    Optional<Order> findOrderById(String id);
    List<Order> findOrdersByCustomerId(String customerId);
}
