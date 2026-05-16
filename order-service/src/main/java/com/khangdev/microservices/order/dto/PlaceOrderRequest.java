package com.khangdev.microservices.order.dto;

import java.math.BigDecimal;


public record PlaceOrderRequest (Long id, String orderNumber, String skuCode,
                                 BigDecimal price, Integer quantity) { }
