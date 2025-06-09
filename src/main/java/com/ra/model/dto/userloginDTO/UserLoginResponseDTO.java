package com.ra.model.dto.userloginDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginResponseDTO {
    private String username;
    private String typeToken;
    private String accessToken;

}
