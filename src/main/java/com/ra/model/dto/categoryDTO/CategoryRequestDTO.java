package com.ra.model.dto.categoryDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryRequestDTO {
    @NotBlank(message = "Category name cannot be blank")
    private String name;

    private Boolean status;
}