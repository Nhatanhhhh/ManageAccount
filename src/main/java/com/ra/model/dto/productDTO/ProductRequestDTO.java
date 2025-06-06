package com.ra.model.dto.productDTO;

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
public class ProductRequestDTO {
    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private double price;

    @Min(value = 0, message = "Stock must be greater than or equal to 0")
    private int stock;

    private Long categoryId;

    private Boolean status;
}