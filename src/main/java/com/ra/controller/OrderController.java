package com.ra.controller;

import com.ra.model.dto.orderDTO.OrderResponseDTO;
import com.ra.service.order.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/user/orders")
    public ResponseEntity<Page<OrderResponseDTO>> getAllOrders(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "3") int limit,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "orderBy", defaultValue = "asc") String orderBy
    ) {
        Sort sort = orderBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<OrderResponseDTO> orders = orderService.getAllOrders(pageable);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PatchMapping("/admin/orders/status/{id}")
    public ResponseEntity<OrderResponseDTO> changeOrderStatus(@PathVariable Long id, @RequestParam Boolean status) {
        OrderResponseDTO orderResponseDTO = orderService.changeOrderStatus(id, status);
        return new ResponseEntity<>(orderResponseDTO, HttpStatus.OK);
    }
}