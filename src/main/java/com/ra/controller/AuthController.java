package com.ra.controller;

import com.ra.model.dto.userloginDTO.UserLoginRequestDTO;
import com.ra.model.dto.userloginDTO.UserLoginResponseDTO;
import com.ra.model.dto.userregisterDTO.UserRegisterRequestDTO;
import com.ra.model.dto.userregisterDTO.UserRegisterResponseDTO;
import com.ra.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDTO loginRequest) {
        UserLoginResponseDTO userLoginResponseDTO = authService.login(loginRequest);
        return new ResponseEntity<>(userLoginResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/register")
    @Operation(summary = "register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequestDTO registerRequest) {
        UserRegisterResponseDTO response = authService.register(registerRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
