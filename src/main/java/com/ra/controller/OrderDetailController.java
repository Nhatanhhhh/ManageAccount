package com.ra.controller;

import com.ra.model.dto.orderdetailDTO.OrderDetailResponseDTO;
import com.ra.service.orderdetail.OrderDetailService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/orders/details/{orderId}")
    @Operation(summary = "View order detail")
    public ResponseEntity<List<OrderDetailResponseDTO>> getOrderDetails(@PathVariable Long orderId) {
        List<OrderDetailResponseDTO> orderDetails = orderDetailService.getOrderDetails(orderId);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }
}