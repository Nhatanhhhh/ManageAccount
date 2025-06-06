package com.ra.model.dto.shoppingCartDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ShoppingCartRequestDTO {
    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}