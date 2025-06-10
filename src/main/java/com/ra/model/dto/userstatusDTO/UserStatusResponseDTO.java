package com.ra.model.dto.userstatusDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "User status update response")
public class UserStatusResponseDTO {
    @Schema(description = "User ID", example = "1")
    private Long id;
    @Schema(description = "Email", example = "user@example.com")
    private String email;
    @Schema(description = "Status (true/false)", example = "true")
    private Boolean status;
    @Schema(description = "Message", example = "Status updated successfully")
    private String message;
}
