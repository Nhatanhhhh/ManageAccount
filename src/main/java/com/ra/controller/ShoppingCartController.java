package com.ra.controller;

import com.ra.model.dto.orderDTO.OrderResponseDTO;
import com.ra.model.dto.shoppingCartDTO.ShoppingCartRequestDTO;
import com.ra.model.dto.shoppingCartDTO.ShoppingCartResponseDTO;
import com.ra.service.checkout.CheckoutService;
import com.ra.service.shoppingcart.ShoppingCartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private CheckoutService checkoutService;

    @GetMapping
    public ResponseEntity<List<ShoppingCartResponseDTO>> getAllShoppingCarts() {
        List<ShoppingCartResponseDTO> shoppingCartResponseDTO = shoppingCartService.getAllShoppingCarts();
        return new ResponseEntity<>(shoppingCartResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ShoppingCartResponseDTO> addToCart(@Valid @RequestBody ShoppingCartRequestDTO requestDTO) {
        ShoppingCartResponseDTO shoppingCartResponseDTO = shoppingCartService.addToCart(requestDTO);
        return new ResponseEntity<>(shoppingCartResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/quantity/{cartId}")
    public ResponseEntity<ShoppingCartResponseDTO> updateCartQuantity(@PathVariable Long cartId, @RequestParam int quantity) {
        ShoppingCartResponseDTO response = shoppingCartService.updateCartQuantity(cartId, quantity);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{cartId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long cartId) {
        shoppingCartService.deleteCartItem(cartId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        shoppingCartService.clearCart();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponseDTO> checkout() {
        OrderResponseDTO OrderResponseDTO = checkoutService.checkout();
        return new ResponseEntity<>(OrderResponseDTO, HttpStatus.CREATED);
    }
}
