package com.ra.service.order;

import com.ra.model.dto.orderDTO.OrderResponseDTO;

import java.util.List;

public interface OrderService {
    List<OrderResponseDTO> getAllOrders();
    OrderResponseDTO changeOrderStatus(Long id, Boolean status);

}
