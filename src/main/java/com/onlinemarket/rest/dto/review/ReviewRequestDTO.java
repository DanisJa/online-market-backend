package com.onlinemarket.rest.dto.review;

import com.onlinemarket.core.model.Review;
import com.onlinemarket.core.model.enums.ReviewRating;
import java.util.Date;
import com.onlinemarket.core.model.User;
import com.onlinemarket.core.model.Product;
import jakarta.validation.constraints.NotNull;

public class ReviewRequestDTO {
    private String userId;
    private String productId;
    private ReviewRating rating;
    private String comment;
    private Date createdAt;

    public ReviewRequestDTO() {}
    public ReviewRequestDTO(Review review){
        this.userId = review.getUserId();
        this.productId = review.getProductId();
        this.comment = review.getComment();
        this.createdAt = review.getCreatedAt();
        this.rating = review.getRating();
    }

    public Review toEntity(){
        Review review = new Review();
        review.setComment(this.comment);
        review.setUserId(this.userId);
        review.setRating(this.rating);
        review.setCreatedAt(new Date());
        review.setProductId(this.productId);
        return review;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProduct(String productId) {
        this.productId = productId;
    }

    public ReviewRating getRating() {
        return rating;
    }

    public void setRating(ReviewRating rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
