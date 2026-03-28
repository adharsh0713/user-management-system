package com.adharsh.usermanagement.service;

import com.adharsh.usermanagement.dto.request.UserRegistrationRequest;
import com.adharsh.usermanagement.dto.response.UserResponse;
import com.adharsh.usermanagement.exception.UserAlreadyExistsException;
import com.adharsh.usermanagement.model.User;
import com.adharsh.usermanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse registerUser(UserRegistrationRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())){
            throw new UserAlreadyExistsException("Email already exists.");
        }

        String hash = passwordEncoder.encode(request.getPassword());

        User user = User.create(
                request.getEmail(),
                request.getName(),
                hash,
                Instant.now()
        );

        userRepository.save(user);

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.isActive()
        );
    }
}
