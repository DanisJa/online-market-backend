package com.onlinemarket.rest.dto.review;

import com.onlinemarket.core.model.Review;
import com.onlinemarket.core.model.enums.ReviewRating;
import com.onlinemarket.rest.dto.product.ProductDTO;
import com.onlinemarket.rest.dto.user.UserDTO;

import java.util.Date;

public class ReviewDetailsDTO {
    private String id, comment;
    private UserDTO reviewer;
    private ProductDTO product;
    private ReviewRating reviewRating;
    private Date createdAt;

    public ReviewDetailsDTO(Review review, UserDTO reviewer, ProductDTO product){
        this.id = review.getId();
        this.comment = review.getComment();
        this.reviewRating = review.getRating();
        this.createdAt = review.getCreatedAt();
        this.reviewer = reviewer;
        this.product = product;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserDTO getReviewer() {
        return reviewer;
    }

    public void setReviewer(UserDTO reviewer) {
        this.reviewer = reviewer;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public ReviewRating getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(ReviewRating reviewRating) {
        this.reviewRating = reviewRating;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
