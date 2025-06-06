package com.ra.service.orderdetail;

import com.ra.model.dto.orderdetailDTO.OrderDetailResponseDTO;
import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.User;
import com.ra.repository.OrderDetailRepository;
import com.ra.repository.OrderRepository;
import com.ra.security.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    private User getCurrentUser() {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrinciple.getUser();
    }

    // Hiển thị tất cả order detail thông qua order
    public List<OrderDetailResponseDTO> getOrderDetails(Long orderId) {
        User user = getCurrentUser();
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to order");
        }
        return orderDetailRepository.findByOrderId(orderId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    private OrderDetailResponseDTO convertToResponseDTO(OrderDetail orderDetail) {
        return OrderDetailResponseDTO.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .productName(orderDetail.getProduct().getName())
                .quantity(orderDetail.getQuantity())
                .unitPrice(orderDetail.getUnitPrice())
                .build();
    }
}