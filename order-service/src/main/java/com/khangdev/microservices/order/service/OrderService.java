package com.khangdev.microservices.order.service;

import com.khangdev.microservices.order.client.InventoryClient;
import com.khangdev.microservices.order.dto.OrderResponse;
import com.khangdev.microservices.order.dto.PlaceOrderRequest;
import com.khangdev.microservices.order.entity.Order;
import com.khangdev.microservices.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    @Transactional
    public OrderResponse placeOrder(PlaceOrderRequest request){
        if(!inventoryClient.isInStock(request.skuCode(), request.quantity())){
            throw new RuntimeException("Product is not in stock with sku code: " + request.skuCode());
        }
        Order newOrder = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .skuCode(request.skuCode())
                .price(request.price())
                .quantity(request.quantity())
                .build();
        newOrder = orderRepository.save(newOrder);
        log.info("Place order successfully");
        return mapToOrderResponse(newOrder);
    }

    public List<OrderResponse> getAllOrders(){
        return orderRepository.findAll().stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    public OrderResponse getById(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return mapToOrderResponse(order);
    }

    public OrderResponse updateOrder(Long id, PlaceOrderRequest request){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        if(request.orderNumber() != null){
            order.setOrderNumber(request.orderNumber());
        }
        if(request.skuCode() != null){
            order.setSkuCode(request.skuCode());
        }
        if(request.price() != null){
            order.setPrice(request.price());
        }
        if(request.quantity() != null){
            order.setQuantity(request.quantity());
        }
        order = orderRepository.save(order);
        log.info("Update order successfully");
        return mapToOrderResponse(order);
    }

    public void deleteOrder(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        orderRepository.delete(order);
        log.info("Delete order successfully");
    }

    private OrderResponse mapToOrderResponse(Order order){
        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getSkuCode(),
                order.getPrice(),
                order.getQuantity()
        );
    }
}
