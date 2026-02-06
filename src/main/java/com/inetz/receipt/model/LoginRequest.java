package com.inetz.receipt.model;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
public class LoginRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;
}
