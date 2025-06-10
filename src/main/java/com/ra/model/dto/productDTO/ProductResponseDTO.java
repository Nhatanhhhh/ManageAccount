package com.ra.model.dto.productDTO;

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
@Schema(description = "Product response")
public class ProductResponseDTO {
    @Schema(description = "Product ID", example = "1")
    private Long id;
    @Schema(description = "Product name", example = "Laptop")
    private String name;
    @Schema(description = "Price", example = "999.99")
    private double price;
    @Schema(description = "Stock quantity", example = "100")
    private int stock;
    @Schema(description = "Category ID", example = "1")
    private Long categoryId;
    @Schema(description = "Status (true/false)", example = "true")
    private Boolean status;
}