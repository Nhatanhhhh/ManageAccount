package com.ra.controller;

import com.ra.model.dto.categoryDTO.CategoryResponseDTO;
import com.ra.model.dto.usermanageDTO.UserManageResponseDTO;
import com.ra.model.dto.userstatusDTO.UserStatusRequestDTO;
import com.ra.model.dto.userstatusDTO.UserStatusResponseDTO;
import com.ra.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/users")
public class UserController {
    @Autowired
    private UserService usersService;

    @GetMapping
    public ResponseEntity<Page<UserManageResponseDTO>> getAllUsers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "3") int limit,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "orderBy", defaultValue = "asc") String orderBy
    ) {
        Sort sort = orderBy.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<UserManageResponseDTO> users = usersService.getAllUsers(pageable);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/change-status/{id}")
    public ResponseEntity<UserStatusResponseDTO> changeUserStatus(@PathVariable Long id,@Valid @RequestBody UserStatusRequestDTO request) {
        UserStatusResponseDTO response = usersService.changeUserStatus(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
