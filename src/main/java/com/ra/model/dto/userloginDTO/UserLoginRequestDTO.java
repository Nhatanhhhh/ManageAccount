package com.ra.model.dto.userloginDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginRequestDTO {
    private String username;
    private String password;
}
