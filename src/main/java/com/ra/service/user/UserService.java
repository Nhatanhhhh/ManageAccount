package com.ra.service.user;

import com.ra.model.dto.usermanageDTO.UserManageResponseDTO;
import com.ra.model.dto.userstatusDTO.UserStatusRequestDTO;
import com.ra.model.dto.userstatusDTO.UserStatusResponseDTO;

import java.util.List;

public interface UserService {
    List<UserManageResponseDTO> getAllUsers();
    UserStatusResponseDTO changeUserStatus(UserStatusRequestDTO request);
}
