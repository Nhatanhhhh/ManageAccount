package com.ra.repository;

import com.ra.model.dto.usermanageDTO.UserManageRequestDTO;
import com.ra.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Optional<User> findById(Long id);
}
