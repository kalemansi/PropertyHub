

package com.example.propertyportal;

import com.example.propertyportal.entity.Role;
import com.example.propertyportal.entity.User;
import com.example.propertyportal.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminAccountInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminAccountInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        boolean adminExists = userRepository.findAll()
                .stream()
                .anyMatch(user -> user.getRole() == Role.ADMIN);

        if (!adminExists) {

            User admin = new User();

            admin.setFullName("PropertyHub Admin");
            admin.setEmail("admin@p.com");
            admin.setPhoneNumber("9999999999");

            // Replace this with your own strong password.
            admin.setPassword(
                    passwordEncoder.encode("Pass@123")
            );

            admin.setRole(Role.ADMIN);

            userRepository.save(admin);

            System.out.println("Admin account created successfully!");
        }
    }
}
