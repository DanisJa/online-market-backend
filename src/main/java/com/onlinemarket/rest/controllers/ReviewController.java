package com.onlinemarket.rest.controllers;

import com.onlinemarket.core.service.ReviewService;
import com.onlinemarket.rest.dto.review.ReviewDTO;
import com.onlinemarket.rest.dto.review.ReviewDetailsDTO;
import com.onlinemarket.rest.dto.review.ReviewRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/reviews")
@SecurityRequirement(name = "jwt-auth")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {this.reviewService = reviewService;}

    @GetMapping
    public List<ReviewDetailsDTO> findAll(){return reviewService.findAll();}

    @GetMapping("/{id}")
    public ReviewDetailsDTO findById(@PathVariable String id){return reviewService.findById(id);}

    @GetMapping("/byProduct")
    public List<ReviewDetailsDTO> findByProduct(@RequestParam String productId){return reviewService.findByProduct(productId);}

    @GetMapping("/byUser")
    public List<ReviewDetailsDTO> findByUser(@RequestParam String userId){return reviewService.findByUser(userId);}

    @PostMapping
    public ReviewDTO addReview(@RequestBody ReviewRequestDTO payload){return reviewService.addReview(payload);}

    @PutMapping("/{id}")
    public ReviewDTO updateReview(@RequestBody ReviewRequestDTO payload, @PathVariable String id){return reviewService.updateReview(id, payload);}

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable String id){
        reviewService.deleteReview(id);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}

