package com.khangdev.microservices.inventory.repository;

import com.khangdev.microservices.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsBySkuCodeAndQuantityIsGreaterThanEqual(String skuCode, Integer quantity);
    boolean existsBySkuCode(String skuCode);
}
