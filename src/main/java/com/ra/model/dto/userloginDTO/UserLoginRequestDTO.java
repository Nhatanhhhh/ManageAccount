package com.ra.model.dto.userloginDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "Login request")
public class UserLoginRequestDTO {
    @Schema(description = "Username", example = "user123")
    private String username;
    @Schema(description = "Password", example = "password123")
    private String password;
}
