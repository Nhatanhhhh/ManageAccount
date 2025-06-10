package com.ra.service.checkout;

import com.ra.model.dto.orderDTO.OrderResponseDTO;
import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.ShoppingCart;
import com.ra.model.entity.User;
import com.ra.repository.OrderDetailRepository;
import com.ra.repository.OrderRepository;
import com.ra.repository.ProductRepository;
import com.ra.repository.ShoppingCartRepository;
import com.ra.security.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    private User getCurrentUser() {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrinciple.getUser();
    }

    @Transactional
    @Override
    @PreAuthorize("hasAuthority('USER')")
    public OrderResponseDTO checkout(Pageable pageable) {
        User user = getCurrentUser();
        Page<ShoppingCart> cartItemsPage = shoppingCartRepository.findByUser(user, pageable);
        if (cartItemsPage.isEmpty()) {
            throw new RuntimeException("No items in the requested page of the shopping cart");
        }

        List<ShoppingCart> cartItems = cartItemsPage.getContent();

        for (ShoppingCart cartItem : cartItems) {
            if (cartItem.getProduct().getStock() < cartItem.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + cartItem.getProduct().getName());
            }
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setReceiveDate(LocalDate.now().plusDays(3));
        order.setStatus(true);
        Order savedOrder = orderRepository.save(order);

        for (ShoppingCart cartItem : cartItems) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(savedOrder);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setUnitPrice(cartItem.getProduct().getPrice());
            orderDetailRepository.save(orderDetail);

            cartItem.getProduct().setStock(cartItem.getProduct().getStock() - cartItem.getQuantity());
            productRepository.save(cartItem.getProduct());
        }

        shoppingCartRepository.deleteAll(cartItems);

        return OrderResponseDTO.builder()
                .id(savedOrder.getId())
                .orderDate(savedOrder.getOrderDate())
                .receiveDate(savedOrder.getReceiveDate())
                .status(savedOrder.getStatus())
                .build();
    }
}
