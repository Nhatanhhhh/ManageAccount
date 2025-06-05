package com.ra.service.auth;

import com.ra.model.dto.userloginDTO.UserLoginRequestDTO;
import com.ra.model.dto.userloginDTO.UserLoginResponseDTO;
import com.ra.model.dto.userregisterDTO.UserRegisterRequestDTO;
import com.ra.model.dto.userregisterDTO.UserRegisterResponseDTO;
import com.ra.model.entity.Role;
import com.ra.model.entity.User;
import com.ra.repository.RoleRepository;
import com.ra.repository.UserRepository;
import com.ra.security.UserPrinciple;
import com.ra.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserLoginResponseDTO login(UserLoginRequestDTO userLoginRequestDTO) {
        Authentication authentication = authenticationProvider
                .authenticate(new UsernamePasswordAuthenticationToken(
                        userLoginRequestDTO.getUsername(),
                        userLoginRequestDTO.getPassword()));
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        return UserLoginResponseDTO.builder()
                .username(userPrinciple.getUsername())
                .typeToken("Bearer Token")
                .accessToken(jwtProvider.generateToken(userPrinciple))
                .build();
    }

    @Override
    public UserRegisterResponseDTO register(UserRegisterRequestDTO registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFullName(registerRequest.getFullName());
        user.setAddress(registerRequest.getAddress());
        user.setPhone(registerRequest.getPhone());
        user.setStatus(true);

        Role userRole = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RuntimeException("Role 'USER' not found in the database"));
        user.setRoles(new HashSet<>(Set.of(userRole)));

        User savedUser = userRepository.save(user);

        return UserRegisterResponseDTO.builder()
                .id(savedUser.getId())
                .username(savedUser.getEmail())
                .fullName(savedUser.getFullName())
                .message("Register successful")
                .success(true)
                .build();
    }
}
