package com.ra.model.dto.userregisterDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jdk.jfr.BooleanFlag;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterRequestDTO {
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 6, max = 50, message = "Username must be between 6 and 50 characters")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Full name cannot be blank")
    private String fullName;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 250, message = "Address must not exceed 250 characters")
    private String address;

    @NotBlank(message = "Phone number cannot be blank")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    private String phone;
}