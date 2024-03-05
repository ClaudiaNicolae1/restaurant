package com.restaurant.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurant.models.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}