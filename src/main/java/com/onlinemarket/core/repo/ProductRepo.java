package com.onlinemarket.core.repo;

import com.onlinemarket.core.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends MongoRepository<Product, String> {
    Optional<Product> findProductById(String id);
    Page<Product> findAll(Pageable pageable);
    List<Product> findAll();
    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
    List<Product> findByNameContains(String regexString);

    List<Product> findProductsByCategory(String category);
    List<Product> findProductsByNameContaining(String nameFilter);
    List<Product> findProductsBySellerId(String sellerId);
}
