

package com.example.propertyportal.service;

import com.example.propertyportal.dto.RegisterRequest;
import com.example.propertyportal.dto.UserResponse;
import com.example.propertyportal.entity.Role;
import com.example.propertyportal.entity.User;
import com.example.propertyportal.repository.UserRepository;
import com.example.propertyportal.exception.DuplicateResourceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserResponse registerUser(RegisterRequest request) {


        String email = request.getEmail().trim().toLowerCase();


        if (userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("Email is already registered");
        }



        if (request.getRole() == Role.ADMIN) {
            throw new RuntimeException("Admin registration is not allowed");
        }


        User user = new User();

        user.setFullName(request.getFullName().trim());
        user.setEmail(email);


        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setPhoneNumber(request.getPhoneNumber());
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