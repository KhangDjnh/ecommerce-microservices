package com.khangdev.microservices.inventory.controller;

import com.khangdev.microservices.inventory.dto.InventoryRequest;
import com.khangdev.microservices.inventory.dto.InventoryResponse;
import com.khangdev.microservices.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponse createInventory(@RequestBody InventoryRequest request) {
        return inventoryService.createInventory(request);
    }

    @GetMapping("/is-in-stock")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
        return inventoryService.isInStock(skuCode, quantity);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> getAllInventories() {
        return inventoryService.getAllInventories();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse getById(@PathVariable Long id){
        return inventoryService.getById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse updateInventory(@PathVariable Long id, @RequestBody InventoryRequest request){
        return inventoryService.updateInventory(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteInventory(@PathVariable Long id){
        inventoryService.deleteInventory(id);
        return "Delete Inventory successfully";
    }
}
