package com.ra.service.user;

import com.ra.model.dto.usermanageDTO.UserManageResponseDTO;
import com.ra.model.dto.userstatusDTO.UserStatusRequestDTO;
import com.ra.model.dto.userstatusDTO.UserStatusResponseDTO;
import com.ra.model.entity.User;
import com.ra.repository.RoleRepository;
import com.ra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class UserSerivceImpl implements UserService{
    @Autowired
    private UserRepository usersRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public Page<UserManageResponseDTO> getAllUsers(Pageable pageable) {
        Page<User> users = usersRepository.findAll(pageable);
        Page<UserManageResponseDTO> usersResponseDTOs;

        usersResponseDTOs = users.map(user -> UserManageResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .address(user.getAddress())
                .phone(user.getPhone())
                .status(user.getStatus())
                .build()
        );
        return usersResponseDTOs;
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserStatusResponseDTO changeUserStatus(UserStatusRequestDTO request) {
        User user = usersRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + request.getUserId()));

        user.setStatus(request.getStatus());

        User updatedUser = usersRepository.save(user);

        return UserStatusResponseDTO.builder()
                .id(updatedUser.getId())
                .email(updatedUser.getEmail())
                .status(updatedUser.getStatus())
                .message("User status updated successfully")
                .build();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void addRoleToUser(Long userId, Long roleId) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        var role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + roleId));
        user.getRoles().add(role);
        usersRepository.save(user);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER') and #userId > 0")
    public void removeRoleFromUser(Long userId, Long roleId) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        var role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + roleId));
        user.getRoles().remove(role);
        usersRepository.save(user);
    }
}
