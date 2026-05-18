package com.khangdev.microservices.inventory.service;

import com.khangdev.microservices.inventory.dto.InventoryRequest;
import com.khangdev.microservices.inventory.dto.InventoryResponse;
import com.khangdev.microservices.inventory.entity.Inventory;
import com.khangdev.microservices.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional
    public InventoryResponse createInventory (InventoryRequest request){
        if(inventoryRepository.existsBySkuCode(request.skuCode())){
            throw new RuntimeException("Inventory already exists with sku code: " + request.skuCode());
        }
        Inventory inventory = Inventory.builder()
                .skuCode(request.skuCode())
                .quantity(request.quantity())
                .build();
        inventory = inventoryRepository.save(inventory);
        return toInventoryResponse(inventory);
    }

    public boolean isInStock(String skuCode, Integer quantity){
        return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
    }

    public List<InventoryResponse> getAllInventories(){
        return inventoryRepository.findAll().stream()
                .map(this::toInventoryResponse)
                .toList();
    }

    public InventoryResponse getById(Long id){
        return toInventoryResponse(inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id)));
    }

    @Transactional
    public InventoryResponse updateInventory(Long id, InventoryRequest request){
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id));
        if(request.skuCode() != null){
            inventory.setSkuCode(request.skuCode());
        }
        if(request.quantity() != null){
            inventory.setQuantity(request.quantity());
        }
        inventory = inventoryRepository.save(inventory);
        log.info("Update inventory successfully");
        return toInventoryResponse(inventory);
    }

    public void deleteInventory(Long id){
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id));
        inventoryRepository.delete(inventory);
        log.info("Delete inventory successfully");
    }

    private InventoryResponse toInventoryResponse(Inventory inventory){
        return new InventoryResponse(inventory.getId(), inventory.getSkuCode(), inventory.getQuantity());
    }
}
