package com.ra.model.dto.orderdetailDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrderDetailResponseDTO {
    private Long id;
    private Long orderId;
    private Long productId;
    private String productName;
    private int quantity;
    private double unitPrice;
}