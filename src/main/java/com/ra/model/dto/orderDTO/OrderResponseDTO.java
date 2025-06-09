package com.ra.model.dto.orderDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "Order response")
public class OrderResponseDTO {
    @Schema(description = "Order ID", example = "1")
    private Long id;
    @Schema(description = "Order date", example = "2025-06-09")
    private LocalDate orderDate;
    @Schema(description = "Receive date", example = "2025-06-12")
    private LocalDate receiveDate;
    @Schema(description = "Status (true/false)", example = "true")
    private Boolean status;
}