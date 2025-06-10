package com.ra.service.order;

import com.ra.model.dto.orderDTO.OrderResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    Page<OrderResponseDTO> getAllOrders(Pageable pageable);
    OrderResponseDTO changeOrderStatus(Long id, Boolean status);

}
