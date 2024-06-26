package com.onlinemarket.rest.dto.review;

import com.onlinemarket.core.model.Product;
import com.onlinemarket.core.model.Review;
import com.onlinemarket.core.model.User;
import com.onlinemarket.core.model.enums.ReviewRating;

import java.util.Date;

public class ReviewDTO {
    private String id, comment;
    private String userId;
    private String productId;
    private ReviewRating reviewRating;
    private Date createdAt;

    public ReviewDTO(Review review){
        this.id = review.getId();
        this.comment = review.getComment();
        this.userId = review.getUserId();
        this.productId = review.getProductId();
        this.reviewRating = review.getRating();
        this.createdAt = review.getCreatedAt();
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String user) {
        this.userId = user;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {this.productId = productId;}

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
