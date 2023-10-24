package com.onlinemarket.rest.dto.review;

import com.onlinemarket.core.model.Review;
import com.onlinemarket.core.model.enums.ReviewRating;
import java.util.Date;
import com.onlinemarket.core.model.User;
import com.onlinemarket.core.model.Product;

public class ReviewRequestDTO {
    private User user;
    private Product product;
    private ReviewRating rating;
    private String comment;
    private Date createdAt;

    public ReviewRequestDTO() {}
    public ReviewRequestDTO(Review review){
        this.user = review.getUser();
        this.product = review.getProduct();
        this.comment = review.getComment();
        this.createdAt = review.getCreatedAt();
        this.rating = review.getRating();
    }

    public Review toEntity(){
        Review review = new Review();
        review.setComment(this.comment);
        review.setUser(this.user);
        review.setRating(this.rating);
        review.setCreatedAt(new Date());
        review.setProduct(this.product);
        return review;
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
