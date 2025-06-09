package com.ra.controller;

import com.ra.model.dto.orderDTO.OrderResponseDTO;
import com.ra.model.dto.shoppingCartDTO.ShoppingCartRequestDTO;
import com.ra.model.dto.shoppingCartDTO.ShoppingCartResponseDTO;
import com.ra.service.checkout.CheckoutService;
import com.ra.service.shoppingcart.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of cart items",
                    content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid sortBy", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PreAuthorize("hasAuthority('USER')")
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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item added",
                    content = @Content(schema = @Schema(implementation = ShoppingCartResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ShoppingCartResponseDTO> addToCart(@Valid @RequestBody ShoppingCartRequestDTO requestDTO) {
        ShoppingCartResponseDTO shoppingCartResponseDTO = shoppingCartService.addToCart(requestDTO);
        return new ResponseEntity<>(shoppingCartResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("/quantity/{cartId}")
    @Operation(summary = "Edit quantity product in cart")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quantity updated",
                    content = @Content(schema = @Schema(implementation = ShoppingCartResponseDTO.class))),
            @ApiResponse(responseCode = "204", description = "No content", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid quantity", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cart item not found", content = @Content)
    })
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ShoppingCartResponseDTO> updateCartQuantity(@PathVariable Long cartId, @RequestParam int quantity) {
        ShoppingCartResponseDTO response = shoppingCartService.updateCartQuantity(cartId, quantity);
        return response != null ? ResponseEntity.ok(response) : ResponseEntity.noContent().build();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item deleted", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cart item not found", content = @Content)
    })
    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/delete/{cartId}")
    @Operation(summary = "Delete product in cart")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long cartId) {
        shoppingCartService.deleteCartItem(cartId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cart cleared", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Clear all product in cart")
    public ResponseEntity<Void> clearCart() {
        shoppingCartService.clearCart();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/checkout")
    @Operation(summary = "Checkout")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created",
                    content = @Content(schema = @Schema(implementation = OrderResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<OrderResponseDTO> checkout(Pageable pageable) {
        OrderResponseDTO OrderResponseDTO = checkoutService.checkout(pageable);
        return new ResponseEntity<>(OrderResponseDTO, HttpStatus.CREATED);
    }
}
