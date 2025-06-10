package com.ra.service.checkout;

import com.ra.model.dto.orderDTO.OrderResponseDTO;
import org.springframework.data.domain.Pageable;

public interface CheckoutService {
    OrderResponseDTO checkout(Pageable pageable);
}
