package com.adharsh.usermanagement.service;

import com.adharsh.usermanagement.dto.request.UserLoginRequest;
import com.adharsh.usermanagement.dto.request.UserRegistrationRequest;
import com.adharsh.usermanagement.dto.response.UserLoginResponse;
import com.adharsh.usermanagement.dto.response.UserRegistrationResponse;
import com.adharsh.usermanagement.exception.InvalidPasswordException;
import com.adharsh.usermanagement.exception.UserAlreadyExistsException;
import com.adharsh.usermanagement.exception.UserDoesNotExistsException;
import com.adharsh.usermanagement.model.User;
import com.adharsh.usermanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserRegistrationResponse registerUser(UserRegistrationRequest request) {
        String email = request.getEmail().trim().toLowerCase();
        if (userRepository.existsByEmailIgnoreCase(email)){
            throw new UserAlreadyExistsException("Email already exists.");
        }

        String hash = passwordEncoder.encode(request.getPassword());

        User user = User.create(
                email,
                request.getName().trim(),
                hash
        );

        userRepository.save(user);
        log.info("User created with email: {}", email);

        return new UserRegistrationResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.isActive()
        );
    }

    public UserLoginResponse loginUser(UserLoginRequest request){
        String email = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> {
                    log.warn("Login attempt with non-existing email: {}", email);
                    return new UserDoesNotExistsException("Invalid credentials.");
                });

        if (!user.isActive()) {
            throw new RuntimeException("Account disabled");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())){
            log.warn("Invalid login attempt with email: {}", email);
            throw new InvalidPasswordException("Invalid credentials.");
        }

        log.info("User logged with email: {}", email);

        return new UserLoginResponse(
                user.getEmail(),
                "Login Successful."
        );
    }
}
