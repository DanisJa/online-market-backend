package com.onlinemarket.rest.controllers;

import com.onlinemarket.core.exceptions.repository.ResourceNotFoundException;
import com.onlinemarket.core.service.ReviewService;
import com.onlinemarket.rest.dto.responses.ApiResponse;
import com.onlinemarket.rest.dto.review.ReviewDTO;
import com.onlinemarket.rest.dto.review.ReviewDetailsDTO;
import com.onlinemarket.rest.dto.review.ReviewRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/reviews")
@SecurityRequirement(name = "jwt-auth")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {this.reviewService = reviewService;}

    @GetMapping
    public ApiResponse<List<ReviewDetailsDTO>> findAll(){
        try {
            return new ApiResponse<>(true, reviewService.findAll());
        } catch(Exception error){
            return new ApiResponse<>(false, error.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewDetailsDTO>> findById(@PathVariable String id){
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, reviewService.findById(id)));
        }catch(ResourceNotFoundException error){
            return ResponseEntity.status(404).body(new ApiResponse<>(false, error.getMessage()));
        } catch(Exception error){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, error.getMessage()));
        }
    }

    @GetMapping("/product/{productId}")
    public List<ReviewDetailsDTO> findByProduct(@PathVariable String productId){return reviewService.findByProduct(productId);}

    @GetMapping("/byUser")
    public ResponseEntity<ApiResponse<List<ReviewDetailsDTO>>> findByUser(@RequestParam String userId){
        try{
            return ResponseEntity.ok(new ApiResponse<>(true, reviewService.findByUser(userId)));
        } catch(ResourceNotFoundException error){
            return ResponseEntity.status(404).body(new ApiResponse<>(false, error.getMessage()));
        } catch(Exception error){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, error.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewDTO>> addReview(@RequestBody ReviewRequestDTO payload){
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, reviewService.addReview(payload)));
        }catch(ResourceNotFoundException error){
            return ResponseEntity.status(404).body(new ApiResponse<>(false, error.getMessage()));
        } catch(Exception error){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, error.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReviewDTO>> updateReview(@RequestBody ReviewRequestDTO payload, @PathVariable String id){
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, reviewService.updateReview(id, payload)));
        } catch(ResourceNotFoundException error){
            return ResponseEntity.status(404).body(new ApiResponse<>(false, error.getMessage()));
        } catch(Exception error){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, error.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteReview(@PathVariable String id){
        try {
            reviewService.deleteReview(id);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } catch(ResourceNotFoundException error){
            return ResponseEntity.status(404).body(new ApiResponse<>(false, error.getMessage()));
        } catch(Exception error){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, error.getMessage()));
        }
    }
}

