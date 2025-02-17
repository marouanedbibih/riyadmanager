package com.marouanedbibih.riyadmanager.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    String username,

    @Size(max = 50, message = "First name cannot exceed 50 characters")
    String firstName,

    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    String lastName,

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    String email,

    @Size(max = 15, message = "Phone number cannot exceed 15 characters")
    String phone,

    @NotBlank(message = "Password is required")
    String password
) {}
