package com.onlinemarket.rest.controllers;

import com.onlinemarket.core.model.Product;
import com.onlinemarket.core.model.User;
import com.onlinemarket.core.service.ReviewService;
import com.onlinemarket.rest.dto.review.ReviewDTO;
import com.onlinemarket.rest.dto.review.ReviewRequestDTO;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/api/reviews")
@RestController
public class ReviewController {
    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {this.reviewService = reviewService;}

    @GetMapping
    public List<ReviewDTO> findAll(){return reviewService.findAll();}

    @GetMapping("/{id}")
    public ReviewDTO findById(@PathVariable String id){return reviewService.findById(id);}

    @GetMapping("/byProduct")
    public List<ReviewDTO> findByProduct(@RequestBody Product product){return reviewService.findByProduct(product);}

    @GetMapping("/byUser")
    public List<ReviewDTO> findByUser(@RequestBody User seller){return reviewService.findByUser(seller);}

    @PostMapping
    public ReviewDTO addReview(@RequestBody ReviewRequestDTO payload){return reviewService.addReview(payload);}

    @PutMapping("/{id}")
    public ReviewDTO updateReview(@RequestBody ReviewRequestDTO payload, @PathVariable String id){return reviewService.updateReview(id, payload);}

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable String id){
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
