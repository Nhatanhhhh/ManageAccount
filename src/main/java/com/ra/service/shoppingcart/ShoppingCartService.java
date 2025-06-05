package com.ra.service.shoppingcart;

import com.ra.model.dto.shoppingCartDTO.ShoppingCartRequestDTO;
import com.ra.model.dto.shoppingCartDTO.ShoppingCartResponseDTO;

import java.util.List;

public interface ShoppingCartService {
    List<ShoppingCartResponseDTO> getAllShoppingCarts();
    ShoppingCartResponseDTO addToCart(ShoppingCartRequestDTO requestDTO);
    ShoppingCartResponseDTO updateCartQuantity(Long cartId, int quantity);
    void deleteCartItem(Long cartId);
    void clearCart();
}
