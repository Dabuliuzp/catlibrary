package com.example.virtual_campus.controller;

import com.example.virtual_campus.model.Product;
import com.example.virtual_campus.model.User;
import com.example.virtual_campus.repository.ProductRepository;
import com.example.virtual_campus.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Validated @RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
            @Validated @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    public Optional<Product> SearchById(Long id){
        return productRepository.findById(id);
    }

    public Optional<Product> SearchByName(String name){
        return productRepository.findByName(name);
    }

    public Boolean ProductEdit(Long id, String name, Double price, Integer quantity){
        if(!productRepository.existsById(id)) return false;
        Product product = productRepository.findById(id).get();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        updateProduct(id, product);
        return true;
    }

    public boolean ProductDelete(Long id){
        if(!productRepository.existsById(id)) return false;
        Product product = productRepository.findById(id).get();
        productRepository.delete(product);
        return true;
    }

    public boolean ProductAdd(Long id, String name, Double price, Integer quantity){
        if(productRepository.existsById(id)) return false;
        if(productRepository.existsByName(name)) return false;
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        productRepository.save(product);
        return true;
    }
}
