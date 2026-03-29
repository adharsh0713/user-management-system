package com.adharsh.usermanagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserLoginRequest {
    @NotBlank(message = "Email must not be empty.")
    @Email(message = "Invalid email.")
    private String email;

    @NotBlank(message = "Password must not be empty.")
    @Size(min = 8, message = "Password must be atleast 8 characters long.")
    private String password;
}
