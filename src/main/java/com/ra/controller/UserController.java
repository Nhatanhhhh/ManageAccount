package com.ra.controller;

import com.ra.model.dto.usermanageDTO.UserManageResponseDTO;
import com.ra.model.dto.userstatusDTO.UserStatusRequestDTO;
import com.ra.model.dto.userstatusDTO.UserStatusResponseDTO;
import com.ra.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users/")
public class UserController {
    @Autowired
    private UserService usersService;

    @GetMapping
    public ResponseEntity<List<UserManageResponseDTO>> getAllUsers() {
        List<UserManageResponseDTO> users = usersService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/change-status/{id}")
    public ResponseEntity<UserStatusResponseDTO> changeUserStatus(@PathVariable Long id,@Valid @RequestBody UserStatusRequestDTO request) {
        UserStatusResponseDTO response = usersService.changeUserStatus(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
