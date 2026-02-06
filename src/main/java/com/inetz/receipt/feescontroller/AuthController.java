package com.inetz.receipt.feescontroller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.inetz.receipt.model.*;
import com.inetz.receipt.response.ApiResponse;
import com.inetz.receipt.service.UserService;
import com.inetz.receipt.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String token = jwtUtil.generateToken(userDetails);

        String role = userDetails.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        return new ApiResponse<>(
                "Login successful",
                new AuthResponse(token, role),
                true
        );
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/register")
    public ApiResponse<Void> register(
            @Valid @RequestBody AuthRequest request,
            Authentication authentication) {

        String creatorRole = authentication.getAuthorities()
                .iterator()
                .next()
                .getAuthority();

        userService.registerUser(request, creatorRole);

        return new ApiResponse<>(
                "User created successfully",
                null,
                true
        );
    }
}
