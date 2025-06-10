package com.ra.model.dto.userloginDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "Login response")
public class UserLoginResponseDTO {
    @Schema(description = "Username", example = "user123")
    private String username;
    @Schema(description = "Token type", example = "Bearer")
    private String typeToken;
    @Schema(description = "Access token", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String accessToken;

}
