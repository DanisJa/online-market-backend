package com.onlinemarket.core.model;

import com.onlinemarket.core.model.enums.ReviewRating;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Review {
    @Id
    private String id;
    private User user;
    private Product product;

    private String comment;
    private ReviewRating rating;
}
