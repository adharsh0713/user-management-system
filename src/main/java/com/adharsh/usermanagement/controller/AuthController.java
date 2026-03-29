package com.adharsh.usermanagement.controller;

import com.adharsh.usermanagement.dto.request.UserLoginRequest;
import com.adharsh.usermanagement.dto.request.UserRegistrationRequest;
import com.adharsh.usermanagement.dto.response.UserLoginResponse;
import com.adharsh.usermanagement.dto.response.UserRegistrationResponse;
import com.adharsh.usermanagement.security.JwtUtil;
import com.adharsh.usermanagement.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserRegistrationRequest request){
        UserRegistrationResponse response = authService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest request){
        UserLoginResponse response =  authService.loginUser(request);
        return ResponseEntity.ok(response);
    }
}
