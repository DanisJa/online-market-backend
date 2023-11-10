package com.onlinemarket.rest.controllers;

import com.onlinemarket.core.service.ProductService;
import com.onlinemarket.rest.dto.product.ProductDTO;
import com.onlinemarket.rest.dto.product.ProductRequestDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/api/products")
@RestController
@SecurityRequirement(name = "jwt-auth")
public class ProductController {
    private ProductService productService;

    public ProductController(ProductService productService) {this.productService = productService;}

    @GetMapping
    public List<ProductDTO> findAll() {return productService.findAll();}

    @GetMapping("/byCategory")
    public List<ProductDTO> findByCategory(@RequestParam String category){
        return productService.findByCategory(category);
    }

    @GetMapping("/{id}")
    public ProductDTO findById(@PathVariable String id){return productService.findById(id);}

    @GetMapping("/byName")
    public List<ProductDTO> findByName(@RequestParam String name){
        return productService.findByName(name);
    }

    @GetMapping("/bySeller")
    public List<ProductDTO> findBySellerId(@RequestParam String sellerId){
        return productService.findBySeller(sellerId);
    }

    @PostMapping
    public ProductDTO addProduct(@RequestBody ProductRequestDTO payload){
        return productService.addProduct(payload);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@RequestBody ProductRequestDTO payload, @PathVariable String id){
        return productService.updateProduct(id, payload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
