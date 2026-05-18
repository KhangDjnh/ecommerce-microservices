package com.khangdev.microservices.inventory.dto;

public record InventoryRequest (Long id, String skuCode, Integer quantity){
}
