package com.khangdev.microservices.product.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Product {
    @Id
    String id;
    String name;
    String description;
    BigDecimal price;
}
