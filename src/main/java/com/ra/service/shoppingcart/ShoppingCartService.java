package com.ra.service.shoppingcart;

import com.ra.model.dto.shoppingCartDTO.ShoppingCartRequestDTO;
import com.ra.model.dto.shoppingCartDTO.ShoppingCartResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShoppingCartService {
    Page<ShoppingCartResponseDTO> getAllShoppingCarts(Pageable pageable);
    ShoppingCartResponseDTO addToCart(ShoppingCartRequestDTO requestDTO);
    ShoppingCartResponseDTO updateCartQuantity(Long cartId, int quantity);
    void deleteCartItem(Long cartId);
    void clearCart();
}
