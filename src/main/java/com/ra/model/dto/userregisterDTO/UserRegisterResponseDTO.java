package com.ra.model.dto.userregisterDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterResponseDTO {
    private Long id;
    private String username;
    private String fullName;
    private String message;
    private boolean success;
}