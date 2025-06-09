package com.ra.controller;

import com.ra.model.dto.orderDTO.OrderResponseDTO;
import com.ra.model.dto.shoppingCartDTO.ShoppingCartRequestDTO;
import com.ra.model.dto.shoppingCartDTO.ShoppingCartResponseDTO;
import com.ra.service.checkout.CheckoutService;
import com.ra.service.shoppingcart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/cart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private CheckoutService checkoutService;

    @GetMapping
    @Operation(summary = "View all Product in cart")
    public ResponseEntity<Page<ShoppingCartResponseDTO>> getAllShoppingCarts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "3") int limit,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "orderBy", defaultValue = "asc") String orderBy
    ) {
        Sort sort = orderBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<ShoppingCartResponseDTO> responseDTOS = shoppingCartService.getAllShoppingCarts(pageable);
        return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
    }

    @PostMapping("/add")
    @Operation(summary = "Add new product in cart")
    public ResponseEntity<ShoppingCartResponseDTO> addToCart(@Valid @RequestBody ShoppingCartRequestDTO requestDTO) {
        ShoppingCartResponseDTO shoppingCartResponseDTO = shoppingCartService.addToCart(requestDTO);
        return new ResponseEntity<>(shoppingCartResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/quantity/{cartId}")
    @Operation(summary = "Edit quantity product in cart")
    public ResponseEntity<ShoppingCartResponseDTO> updateCartQuantity(@PathVariable Long cartId, @RequestParam int quantity) {
        ShoppingCartResponseDTO response = shoppingCartService.updateCartQuantity(cartId, quantity);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{cartId}")
    @Operation(summary = "Delete product in cart")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long cartId) {
        shoppingCartService.deleteCartItem(cartId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    @Operation(summary = "Clear all product in cart")
    public ResponseEntity<Void> clearCart() {
        shoppingCartService.clearCart();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/checkout")
    @Operation(summary = "Checkout")
    public ResponseEntity<OrderResponseDTO> checkout(Pageable pageable) {
        OrderResponseDTO OrderResponseDTO = checkoutService.checkout(pageable);
        return new ResponseEntity<>(OrderResponseDTO, HttpStatus.CREATED);
    }
}
