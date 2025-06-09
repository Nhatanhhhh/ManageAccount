package com.ra.model.dto.userstatusDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Schema(description = "User status update request")
public class UserStatusRequestDTO {
    @Schema(description = "User ID", example = "1")
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @Schema(description = "Status (true/false)", example = "true")
    @NotNull(message = "Status cannot be null")
    private Boolean status;
}