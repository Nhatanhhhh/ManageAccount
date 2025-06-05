package com.ra.repository;

import com.ra.model.entity.ShoppingCart;
import com.ra.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    List<ShoppingCart> findByUser(User user);
    Optional<ShoppingCart> findByUserAndProductId(User user, Long productId);
}