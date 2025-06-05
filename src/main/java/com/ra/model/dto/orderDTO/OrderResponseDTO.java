package com.ra.model.dto.orderDTO;

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
public class OrderResponseDTO {
    private Long id;
    private LocalDate orderDate;
    private LocalDate receiveDate;
    private Boolean status;
}