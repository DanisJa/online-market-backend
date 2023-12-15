package com.onlinemarket.rest.controllers;

import com.onlinemarket.core.exceptions.general.BadRequestException;
import com.onlinemarket.core.exceptions.repository.ResourceNotFoundException;
import com.onlinemarket.core.service.ProductService;
import com.onlinemarket.rest.dto.product.ProductDTO;
import com.onlinemarket.rest.dto.product.ProductRequestDTO;
import com.onlinemarket.rest.dto.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/api/products")
@RestController
@SecurityRequirement(name = "jwt-auth")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {this.productService = productService;}

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductDTO>>> findAll() {
        return ResponseEntity.ok(new ApiResponse<>(true, productService.findAll()));
    }

    @GetMapping("/byCategory")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> findByCategoryId(@RequestParam String categoryId){
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, productService.findByCategory(categoryId)));
        } catch(ResourceNotFoundException error){
            return ResponseEntity.status(404).body(new ApiResponse<>(false, error.getMessage()));
        } catch(BadRequestException error){
          return ResponseEntity.status(400).body(new ApiResponse<>(false, error.getMessage()));
        } catch(Exception error){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, error.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> findById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, productService.findById(id)));
        } catch(ResourceNotFoundException error){
            return ResponseEntity.status(404).body(new ApiResponse<>(false, error.getMessage()));
        } catch(Exception error){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, error.getMessage()));
        }
    }

    @GetMapping("/byName")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> findByName(@RequestParam String name){
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, productService.findByName(name)));
        } catch(ResourceNotFoundException error){
            return ResponseEntity.status(404).body(new ApiResponse<>(false, error.getMessage()));
        } catch(Exception error){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, error.getMessage()));
        }
    }

    @GetMapping("/bySeller")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> findBySellerId(@RequestParam String sellerId){
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, productService.findBySeller(sellerId)));
        }catch(ResourceNotFoundException error){
            return ResponseEntity.status(404).body(new ApiResponse<>(false, error.getMessage()));
        } catch(Exception error){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, error.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDTO>> addProduct(@RequestBody ProductRequestDTO payload){
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, productService.addProduct(payload)));
        } catch(Exception error){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, error.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDTO>> updateProduct(@RequestBody ProductRequestDTO payload, @PathVariable String id){
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, productService.updateProduct(id, payload)));
        } catch(ResourceNotFoundException error){
            return ResponseEntity.status(404).body(new ApiResponse<>(false, error.getMessage()));
        } catch(Exception error){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, error.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable String id){
        try{
            productService.deleteProduct(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Successfully deleted product."));
        } catch(ResourceNotFoundException error){
            return ResponseEntity.status(404).body(new ApiResponse<>(false, error.getMessage()));
        } catch(Exception error){
            return ResponseEntity.status(500).body(new ApiResponse<>(false, error.getMessage()));
        }
    }
}
