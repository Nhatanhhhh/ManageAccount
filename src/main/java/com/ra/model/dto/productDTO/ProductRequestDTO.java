package com.ra.model.dto.productDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Product creation/update request")
public class ProductRequestDTO {
    @Schema(description = "Product name", example = "Laptop")
    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @Schema(description = "Price", example = "999.99")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private double price;

    @Schema(description = "Stock quantity", example = "100")
    @Min(value = 0, message = "Stock must be greater than or equal to 0")
    private int stock;

    @Schema(description = "Category ID", example = "1")
    private Long categoryId;

    @Schema(description = "Status (true/false)", example = "true")
    private Boolean status;
}