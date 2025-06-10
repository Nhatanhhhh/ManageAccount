package com.ra.controller;

import com.ra.model.dto.userloginDTO.UserLoginRequestDTO;
import com.ra.model.dto.userloginDTO.UserLoginResponseDTO;
import com.ra.model.dto.userregisterDTO.UserRegisterRequestDTO;
import com.ra.model.dto.userregisterDTO.UserRegisterResponseDTO;
import com.ra.service.auth.AuthService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Auth", description = "Controller to Login or regiter")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = UserLoginResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid credentials", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDTO loginRequest) {
        UserLoginResponseDTO userLoginResponseDTO = authService.login(loginRequest);
        return new ResponseEntity<>(userLoginResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/register")
    @Operation(summary = "register")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Registration successful",
                    content = @Content(schema = @Schema(implementation = UserRegisterResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    public ResponseEntity<?> register(@RequestBody UserRegisterRequestDTO registerRequest) {
        UserRegisterResponseDTO response = authService.register(registerRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
