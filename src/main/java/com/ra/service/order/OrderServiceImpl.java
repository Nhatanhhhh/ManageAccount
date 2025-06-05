package com.ra.service.order;

import com.ra.model.dto.orderDTO.OrderResponseDTO;
import com.ra.model.entity.Order;
import com.ra.model.entity.User;
import com.ra.repository.OrderRepository;
import com.ra.security.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    private User getCurrentUser() {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrinciple.getUser();
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        User user = getCurrentUser();
        return orderRepository.findByUser(user).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDTO changeOrderStatus(Long id, Boolean status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return convertToResponseDTO(updatedOrder);
    }

    private OrderResponseDTO convertToResponseDTO(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .receiveDate(order.getReceiveDate())
                .status(order.getStatus())
                .build();
    }
}
