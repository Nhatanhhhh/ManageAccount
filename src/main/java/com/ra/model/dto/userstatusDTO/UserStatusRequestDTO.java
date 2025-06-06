package com.ra.model.dto.userstatusDTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UserStatusRequestDTO {
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Status cannot be null")
    private Boolean status;
}