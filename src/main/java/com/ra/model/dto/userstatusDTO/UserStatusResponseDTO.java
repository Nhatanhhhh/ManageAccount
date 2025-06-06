package com.ra.model.dto.userstatusDTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserStatusResponseDTO {
    private Long id;
    private String email;
    private Boolean status;
    private String message;
    private Boolean success;
}
