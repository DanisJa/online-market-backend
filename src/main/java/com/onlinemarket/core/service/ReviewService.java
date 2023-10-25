package com.onlinemarket.core.service;

import com.onlinemarket.core.repo.ReviewRepo;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private ReviewRepo reviewRepo;

    public ReviewService(ReviewRepo reviewRepo){this.reviewRepo = reviewRepo;}
}
