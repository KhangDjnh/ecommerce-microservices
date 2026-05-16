package com.khangdev.microservices.order.repository;

import com.khangdev.microservices.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
