package com.onlinemarket.core.repo;

import com.onlinemarket.core.model.Product;
import com.onlinemarket.core.model.Review;
import com.onlinemarket.core.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepo extends MongoRepository<Review, String> {
    List<Review> findAll();
    Optional<Review> findReviewById(String id);
    List<Review> findReviewsByProductOrderByCreatedAtDesc(Product product);
    List<Review> findReviewsByUserOrderByCreatedAtDesc(User reviewer);
}
