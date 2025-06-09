package com.ra.model.dto.usermanageDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "User management response")
public class UserManageResponseDTO {
    @Schema(description = "User ID", example = "1")
    private Long id;
    @Schema(description = "Email", example = "user@example.com")
    private String email;
    @Schema(description = "Full name", example = "John Doe")
    private String fullName;
    @Schema(description = "Address", example = "123 Main St")
    private String address;
    @Schema(description = "Phone number", example = "0123456789")
    private String phone;
    @Schema(description = "Status (true/false)", example = "true")
    private Boolean status;
}
