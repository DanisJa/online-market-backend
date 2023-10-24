package com.onlinemarket.rest.dto.review;

import com.onlinemarket.core.model.Product;
import com.onlinemarket.core.model.Review;
import com.onlinemarket.core.model.User;
import com.onlinemarket.core.model.enums.ReviewRating;

import java.util.Date;

public class ReviewDTO {
    private String id, comment;
    private User user;
    private Product product;
    private ReviewRating reviewRating;
    private Date createdAt;

    public ReviewDTO(Review review){
        this.id = review.getId();
        this.comment = review.getComment();
        this.user = review.getUser();
        this.product = review.getProduct();
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
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
