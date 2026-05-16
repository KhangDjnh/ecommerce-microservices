package com.khangdev.microservices.product.repository;

import com.khangdev.microservices.product.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
