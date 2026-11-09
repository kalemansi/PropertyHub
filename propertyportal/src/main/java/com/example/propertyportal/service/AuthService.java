
package com.example.propertyportal.service;

import com.example.propertyportal.dto.LoginRequest;
import com.example.propertyportal.dto.RegisterRequest;
import com.example.propertyportal.dto.UserResponse;
import com.example.propertyportal.entity.Role;
import com.example.propertyportal.entity.User;
import com.example.propertyportal.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // User Login
    public UserResponse login(LoginRequest request) {

        String email = request.getEmail().trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new RuntimeException("Invalid email or password");
        }

        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole(),
                user.getCreatedAt()
        );
    }

    // User Registration
    public UserResponse register(RegisterRequest request) {

        String email = request.getEmail().trim().toLowerCase();


        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email is already registered");
        }


        if (request.getRole() == null) {
            throw new RuntimeException("Please select a valid user role");
        }

        if (request.getRole() == Role.ADMIN) {
            throw new RuntimeException("Admin registration is not allowed");
        }

        User user = new User();

        user.setFullName(request.getFullName().trim());
        user.setEmail(email);
        user.setPhoneNumber(request.getPhoneNumber().trim());


        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        user.setRole(request.getRole());

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getEmail(),
                savedUser.getPhoneNumber(),
                savedUser.getRole(),
                savedUser.getCreatedAt()
        );
    }
}
