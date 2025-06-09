package com.ra.service.orderdetail;

import com.ra.model.dto.orderdetailDTO.OrderDetailResponseDTO;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetailResponseDTO> getOrderDetails(Long orderId);
}
