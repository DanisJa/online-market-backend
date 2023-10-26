package com.onlinemarket.core.service;

import com.onlinemarket.core.exceptions.repository.ResourceNotFoundException;
import com.onlinemarket.core.model.Product;
import com.onlinemarket.core.model.Review;
import com.onlinemarket.core.model.User;
import com.onlinemarket.core.repo.ReviewRepo;
import com.onlinemarket.rest.dto.review.ReviewDTO;
import com.onlinemarket.rest.dto.review.ReviewRequestDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ReviewService {
    private ReviewRepo reviewRepo;

    public ReviewService(ReviewRepo reviewRepo){this.reviewRepo = reviewRepo;}

    public List<ReviewDTO> findAll(){
        List<Review> reviews = reviewRepo.findAll();
        return reviews.stream().map(review -> new ReviewDTO(review)).collect(toList());
    }

    public ReviewDTO findById(String id){
        Optional<Review> review = reviewRepo.findById(id);
        if(review.isEmpty()){
            throw new ResourceNotFoundException("Review with given ID does not exist.");
        }
        return new ReviewDTO(review.get());
    }

    public List<ReviewDTO> findByProduct(Product product){
        List<Review> reviewList = reviewRepo.findReviewsByProductOrderByCreatedAtDesc(product);

        if(reviewList.isEmpty()){
            throw new ResourceNotFoundException("Given product does not have any reviews.");
        }

        List<ReviewDTO> reviews = new ArrayList<>();
        reviewList.stream().map(review -> reviews.add(new ReviewDTO(review))).collect(toList());
        return reviews;
    }

    public List<ReviewDTO> findByUser(User seller){
        List<Review> reviewList = reviewRepo.findReviewsByUserOrderByCreatedAtDesc(seller);

        if(reviewList.isEmpty()){
            throw new ResourceNotFoundException("Given user does not have any reviews.");
        }

        List<ReviewDTO> reviews = new ArrayList<>();
        reviewList.stream().map(review -> reviews.add(new ReviewDTO(review))).collect(toList());
        return reviews;
    }

    public ReviewDTO addReview(ReviewRequestDTO payload) {
        Review review = reviewRepo.save(payload.toEntity());
        return new ReviewDTO(review);
    }

    public ReviewDTO updateReview(String id, ReviewRequestDTO payload){
        Optional<Review> review = reviewRepo.findReviewById(id);
        if(review.isEmpty()){
            throw new ResourceNotFoundException("Review with given ID does not exist");
        }
        Review updatedReview = payload.toEntity();
        updatedReview.setId(review.get().getId());
        updatedReview = reviewRepo.save(updatedReview);
        return new ReviewDTO(updatedReview);
    }

    public void deleteReview(String id){
        Optional<Review> review = reviewRepo.findReviewById(id);
        if(review.isEmpty()){
            throw new ResourceNotFoundException("Review with given ID doesn't exist");
        }
        reviewRepo.delete(review.get());
    }
}
