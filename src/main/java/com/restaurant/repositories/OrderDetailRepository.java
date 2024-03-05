package com.restaurant.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurant.models.entities.OrderDetail;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderId(Long orderId);

}