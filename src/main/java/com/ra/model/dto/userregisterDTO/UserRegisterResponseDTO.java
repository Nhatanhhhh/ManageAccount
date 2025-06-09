package com.ra.model.dto.userregisterDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Register response")
public class UserRegisterResponseDTO {
    @Schema(description = "User ID", example = "1")
    private Long id;
    @Schema(description = "Username", example = "user123")
    private String username;
    @Schema(description = "Full name", example = "John Doe")
    private String fullName;
    @Schema(description = "Message", example = "Registration successful")
    private String message;
    @Schema(description = "Success status", example = "true")
    private boolean success;
}