package com.onlinemarket.core.repo;

import com.onlinemarket.core.model.Product;
import com.onlinemarket.core.model.User;
import com.onlinemarket.rest.dto.user.UserDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends MongoRepository<Product, String> {
    List<Product> findAll();
    Optional<Product> findProductById(String id);
    List<Product> findProductsByCategory(String category);
    List<Product> findProductsByNameContaining(String nameFilter);
    List<Product> findProductsBySeller(UserDTO seller);
}
