package com.ra.controller;

import com.ra.model.dto.orderDTO.OrderResponseDTO;
import com.ra.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/user/orders")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PatchMapping("/admin/orders/status/{id}")
    public ResponseEntity<OrderResponseDTO> changeOrderStatus(@PathVariable Long id, @RequestParam Boolean status) {
        OrderResponseDTO orderResponseDTO = orderService.changeOrderStatus(id, status);
        return new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);
    }
}