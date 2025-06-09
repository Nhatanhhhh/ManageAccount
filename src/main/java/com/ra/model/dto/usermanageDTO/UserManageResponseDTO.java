package com.ra.model.dto.usermanageDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserManageResponseDTO {
    private Long id;
    private String email;
    private String fullName;
    private String address;
    private String phone;
    private Boolean status;
}
