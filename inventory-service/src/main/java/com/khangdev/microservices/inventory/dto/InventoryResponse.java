package com.khangdev.microservices.inventory.dto;

public record InventoryResponse (Long id, String skuCode, Integer quantity) {
}
