package com.ra.model.dto.shoppingCartDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "Cart item response")
public class ShoppingCartResponseDTO {
    @Schema(description = "Cart item ID", example = "1")
    private Long id;
    @Schema(description = "Product ID", example = "1")
    private Long productId;
    @Schema(description = "Product name", example = "Laptop")
    private String productName;
    @Schema(description = "Quantity", example = "2")
    private int quantity;
    @Schema(description = "Unit price", example = "999.99")
    private double unitPrice;
}