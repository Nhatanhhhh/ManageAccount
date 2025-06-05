package com.ra.service.auth;

import com.ra.model.dto.userloginDTO.UserLoginRequestDTO;
import com.ra.model.dto.userloginDTO.UserLoginResponseDTO;
import com.ra.model.dto.userregisterDTO.UserRegisterRequestDTO;
import com.ra.model.dto.userregisterDTO.UserRegisterResponseDTO;

public interface AuthService {
    UserLoginResponseDTO login(UserLoginRequestDTO userLoginRequestDTO);
    UserRegisterResponseDTO register(UserRegisterRequestDTO registerRequest);
}
