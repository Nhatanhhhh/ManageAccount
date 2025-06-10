package com.ra.model.dto.userregisterDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Register request")
public class UserRegisterRequestDTO {
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 6, max = 50, message = "Username must be between 6 and 50 characters")
    @Schema(description = "Email", example = "user@example.com")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Schema(description = "Password", example = "password123")
    private String password;

    @NotBlank(message = "Full name cannot be blank")
    @Schema(description = "Full name", example = "John Doe")
    private String fullName;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 250, message = "Address must not exceed 250 characters")
    @Schema(description = "Address", example = "123 Main St")
    private String address;

    @NotBlank(message = "Phone number cannot be blank")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    @Schema(description = "Phone number", example = "0123456789")
    private String phone;
}