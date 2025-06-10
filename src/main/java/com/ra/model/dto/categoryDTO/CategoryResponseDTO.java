package com.ra.model.dto.categoryDTO;

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
@Schema(description = "Category response")
public class CategoryResponseDTO {
    @Schema(description = "Category ID", example = "1")
    private Long id;
    @Schema(description = "Category name", example = "Electronics")
    private String name;
    @Schema(description = "Status (true/false)", example = "true")
    private Boolean status;
}