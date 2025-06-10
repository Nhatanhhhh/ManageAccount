package com.ra.service.user;

import com.ra.model.dto.usermanageDTO.UserManageResponseDTO;
import com.ra.model.dto.userstatusDTO.UserStatusRequestDTO;
import com.ra.model.dto.userstatusDTO.UserStatusResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserManageResponseDTO> getAllUsers(Pageable pageable);
    UserStatusResponseDTO changeUserStatus(UserStatusRequestDTO request);
    void addRoleToUser(Long userId, Long roleId);
    void removeRoleFromUser(Long userId, Long roleId);
}
