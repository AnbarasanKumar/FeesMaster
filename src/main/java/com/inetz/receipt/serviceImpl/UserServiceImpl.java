package com.inetz.receipt.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.inetz.receipt.entity.Role;
import com.inetz.receipt.entity.User;
import com.inetz.receipt.model.AuthRequest;
import com.inetz.receipt.repositroy.UserRepository;
import com.inetz.receipt.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(AuthRequest request, String creatorRole) {

        Optional<User> existingUser =
                userRepository.findByUsername(request.getUsername());

        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (!creatorRole.equals("ROLE_SUPER_ADMIN")) {
            throw new RuntimeException("Only SUPER_ADMIN can create users");
        }

       /* if (request.getRole() == Role.ROLE_SUPER_ADMIN) {
            throw new RuntimeException("SUPER_ADMIN cannot be created via API");
        } */

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(
                request.getRole() != null ? request.getRole() : Role.ROLE_ADMIN
        );

        return userRepository.save(user);
    }
}
