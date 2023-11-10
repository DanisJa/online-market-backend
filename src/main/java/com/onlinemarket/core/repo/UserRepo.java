package com.onlinemarket.core.repo;

import com.onlinemarket.core.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
    List<User> findAll();
    Optional<User> findUserById(String id);
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
    @Query(value="{$or:[{email:'?0'}, {username:'?0'}]}")
    Optional<User> findByUsernameOrEmail(String username);
}
