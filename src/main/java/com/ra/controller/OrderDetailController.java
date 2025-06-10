package com.ra.controller;

import com.ra.model.dto.orderdetailDTO.OrderDetailResponseDTO;
import com.ra.service.orderdetail.OrderDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "Order Detail", description = "Controller manage order detail")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/orders/details/{orderId}")
    @Operation(summary = "View order detail")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of order details",
                    content = @Content(schema = @Schema(implementation = OrderDetailResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid order ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found", content = @Content)
    })
    public ResponseEntity<List<OrderDetailResponseDTO>> getOrderDetails(@PathVariable Long orderId) {
        List<OrderDetailResponseDTO> orderDetails = orderDetailService.getOrderDetails(orderId);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }
}