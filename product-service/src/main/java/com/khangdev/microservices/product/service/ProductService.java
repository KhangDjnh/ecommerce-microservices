package com.khangdev.microservices.product.service;

import com.khangdev.microservices.product.dto.ProductRequest;
import com.khangdev.microservices.product.dto.ProductResponse;
import com.khangdev.microservices.product.entity.Product;
import com.khangdev.microservices.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest request) {
        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .build();
        product = productRepository.save(product);
        log.info("Product created successfully");
        return mapProductResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapProductResponse)
                .toList();
    }

    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return mapProductResponse(product);
    }

    public ProductResponse updateProduct(String id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        if(request.name() != null){
            product.setName(request.name());
        }
        if(request.description() != null){
            product.setDescription(request.description());
        }
        if(request.price() != null){
            product.setPrice(request.price());
        }
        product = productRepository.save(product);
        log.info("Product updated successfully");
        return mapProductResponse(product);
    }

    public void deleteProduct(String id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        productRepository.delete(product);
        log.info("Product deleted successfully");
    }

    private ProductResponse mapProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
