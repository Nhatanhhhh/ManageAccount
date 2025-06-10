package com.ra.model.dto.shoppingCartDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "Cart item request")
public class ShoppingCartRequestDTO {
    @Schema(description = "Product ID", example = "1")
    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @Schema(description = "Quantity", example = "2")
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}