package com.inetz.receipt.model;

import lombok.*;
import com.inetz.receipt.entity.*;

import jakarta.validation.constraints.*;

@Data
public class AuthRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "Role is required")
    private Role role;
}
