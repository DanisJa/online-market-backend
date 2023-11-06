package com.onlinemarket.core.service;

import com.onlinemarket.core.exceptions.repository.ResourceNotFoundException;
import com.onlinemarket.core.model.Review;
import com.onlinemarket.core.repo.ReviewRepo;
import com.onlinemarket.rest.dto.product.ProductDTO;
import com.onlinemarket.rest.dto.review.ReviewDTO;
import com.onlinemarket.rest.dto.review.ReviewDetailsDTO;
import com.onlinemarket.rest.dto.review.ReviewRequestDTO;
import com.onlinemarket.rest.dto.user.UserDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepo reviewRepo;
    private ProductService productService;
    private UserService userService;

    public ReviewService(ReviewRepo reviewRepo, ProductService productService, UserService userService) {
        this.reviewRepo = reviewRepo;
        this.productService = productService;
        this.userService = userService;
    }

    private List<ReviewDetailsDTO> toReviewDetailsDTOList(List<Review> reviews){
        List<ReviewDetailsDTO> reviewsWithDetails = new ArrayList<>();

        for(Review review : reviews){
            UserDTO reviewer = userService.findById(review.getUserId());
            ProductDTO product = productService.findById(review.getProductId());
            reviewsWithDetails.add(new ReviewDetailsDTO(review, reviewer, product));
        }

        return reviewsWithDetails;
    }

    private ReviewDetailsDTO toReviewDetailsDTO(Review review){
        UserDTO reviewer = userService.findById(review.getUserId());
        ProductDTO product = productService.findById(review.getProductId());

        return new ReviewDetailsDTO(review, reviewer, product);
    }

    public List<ReviewDetailsDTO> findAll(){
        List<Review> reviews = reviewRepo.findAll();
        System.out.println(reviews.get(0));
        return toReviewDetailsDTOList(reviews);
    }

    public ReviewDetailsDTO findById(String id){
        Optional<Review> review = reviewRepo.findById(id);
        if(review.isEmpty()){
            throw new ResourceNotFoundException("Review with given ID does not exist.");
        }
        return toReviewDetailsDTO(review.get());
    }

    public List<ReviewDetailsDTO> findByProduct(String productId){
        List<Review> reviewList = reviewRepo.findReviewsByProductIdOrderByCreatedAtDesc(productId);

        if(reviewList.isEmpty()){
            throw new ResourceNotFoundException("Given product does not have any reviews.");
        }

        return toReviewDetailsDTOList(reviewList);
    }

    public List<ReviewDetailsDTO> findByUser(String sellerId){
        List<Review> reviews = reviewRepo.findReviewsByUserIdOrderByCreatedAtDesc(sellerId);

        if(reviews.isEmpty()){
            throw new ResourceNotFoundException("Given user does not have any reviews.");
        }

        return toReviewDetailsDTOList(reviews);
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
