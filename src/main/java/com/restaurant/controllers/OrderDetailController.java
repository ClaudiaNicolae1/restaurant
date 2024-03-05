package com.restaurant.controllers;
import com.restaurant.models.dtos.OrderDetailDTO;
import com.restaurant.models.entities.OrderDetail;
import com.restaurant.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orderDetails")
public class OrderDetailController {

    private final OrderService orderService;

    @Autowired
    public OrderDetailController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<List<OrderDetailDTO>> getOrderDetailsByOrderId(@PathVariable Long orderId) {
        List<OrderDetailDTO> orderDetails = orderService.getAllOrderDetailsForOrder(orderId);
        return ResponseEntity.ok(orderDetails);
    }

    @GetMapping("/detail/{orderDetailId}")
    public ResponseEntity<OrderDetailDTO> getOrderDetailById(@PathVariable Long orderDetailId) {
        return orderService.getOrderDetailById(orderDetailId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createOrderDetail(@RequestBody OrderDetail orderDetail) {
        try {
            OrderDetail newOrderDetail = orderService.addOrderDetail(orderDetail);
            return ResponseEntity.ok(newOrderDetail);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> updateOrderDetail(@RequestBody OrderDetail orderDetail) {
        try {
            OrderDetail updatedOrderDetail = orderService.updateOrderDetail(orderDetail);
            return ResponseEntity.ok(updatedOrderDetail);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{orderDetailId}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long orderDetailId) {
        orderService.deleteOrderDetail(orderDetailId);
        return ResponseEntity.ok().build();
    }

}