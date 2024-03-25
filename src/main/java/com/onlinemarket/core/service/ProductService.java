package com.onlinemarket.core.service;

import com.onlinemarket.core.exceptions.repository.ResourceNotFoundException;
import com.onlinemarket.core.model.Product;
import com.onlinemarket.core.repo.ProductRepo;
import com.onlinemarket.rest.dto.product.ProductDTO;
import com.onlinemarket.rest.dto.product.ProductRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class ProductService {
    private ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {this.productRepo = productRepo;}

    public List<ProductDTO> findAll() {
        List<Product> products = productRepo.findAll();
        return products.stream().map(ProductDTO::new).collect(toList());
    }

    public List<ProductDTO> findAllWithPagination(Pageable pageable){
        Page<Product> products = productRepo.findAll(pageable);

        return products.stream().map(ProductDTO::new).collect(toList());
    }

    public int countTotalProducts(){
        return (int) productRepo.count();
    }

    public List<ProductDTO> findAllWithSearch(String searchString){
        List<Product> products = productRepo.findByNameContains(searchString);

        return products.stream().map(ProductDTO::new).collect(toList());
    }

    public ProductDTO findById(String id){
        Optional<Product> product = productRepo.findProductById(id);
        if(product.isEmpty()){
            throw new ResourceNotFoundException("Product with given ID does not exist.");
        }
        return new ProductDTO(product.get());
    }

    public List<ProductDTO> findByCategory(String categoryId){
        List<Product> productList = productRepo.findProductsByCategory(categoryId);

        if(productList.isEmpty()){
            throw new ResourceNotFoundException("Products with given category do not exist.");
        }

        List<ProductDTO> products = new ArrayList<>();
        productList.stream().map(product -> products.add(new ProductDTO(product))).collect(toList());
        return products;
    }

    public List<ProductDTO> findByName(String name) {
        List<Product> productList = productRepo.findProductsByNameContaining(name);

        if(productList.isEmpty()){
            throw new ResourceNotFoundException("Products with given name do not exist.");
        }

        List<ProductDTO> products = new ArrayList<>();
        productList.stream().map(product -> products.add(new ProductDTO(product))).collect(toList());
        return products;
    }

    public List<ProductDTO> findBySeller(String sellerId){
        List<Product> productList = productRepo.findProductsBySellerId(sellerId);

        if(productList.isEmpty()){
            throw new ResourceNotFoundException("Products with given seller do not exist.");
        }

        List<ProductDTO> products = new ArrayList<>();
        productList.stream().map(product -> products.add(new ProductDTO(product))).collect(toList());
        return products;
    }

    public ProductDTO addProduct (ProductRequestDTO payload){
        Product product = productRepo.save(payload.toEntity());
        return new ProductDTO(product);
    }

    public ProductDTO updateProduct (String id, ProductRequestDTO payload){
        Optional<Product> product = productRepo.findProductById(id);
        if(product.isEmpty()){
            throw new ResourceNotFoundException("Product with given ID doesn't exist");
        }
        Product updatedProduct = payload.toEntity();
        updatedProduct.setId(product.get().getId());
        updatedProduct = productRepo.save(updatedProduct);
        return new ProductDTO(updatedProduct);
    }

    public void deleteProduct (String id){
        Optional<Product> product = productRepo.findProductById(id);
        if(product.isEmpty()){
            throw new ResourceNotFoundException("Product with given ID doesn't exist.");
        }
        productRepo.delete(product.get());
    }
}
