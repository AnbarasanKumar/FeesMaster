package com.inetz.receipt.model;

import lombok.*;
import com.inetz.receipt.entity.*;

@Data
public class AuthRequest {

    private String username;
    private String password;
    private Role role;
}