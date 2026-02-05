package com.inetz.receipt.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.inetz.receipt.entity.Role;
import com.inetz.receipt.entity.User;
import com.inetz.receipt.repositroy.UserRepository;

@Configuration
public class SuperAdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SuperAdminInitializer(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        boolean superAdminExists =
                userRepository.findAll()
                        .stream()
                        .anyMatch(user -> user.getRole() == Role.ROLE_SUPER_ADMIN);

        if (!superAdminExists) {

            User superAdmin = new User();
            superAdmin.setUsername("anbarasanpno18@gmail.com");
            superAdmin.setPassword(passwordEncoder.encode("Anbu@1234"));
            superAdmin.setRole(Role.ROLE_SUPER_ADMIN);

            userRepository.save(superAdmin);
        }
    }
}
