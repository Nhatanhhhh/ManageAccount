package com.ra.model.dto.categoryDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Category creation/update request")
public class CategoryRequestDTO {
    @NotBlank(message = "Category name cannot be blank")
    @Schema(description = "Category name", example = "Electronics")
    private String name;

    @Schema(description = "Status (true/false)", example = "true")
    private Boolean status;
}