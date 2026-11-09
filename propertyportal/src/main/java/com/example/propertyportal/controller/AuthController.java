
package com.example.propertyportal.controller;

import com.example.propertyportal.dto.LoginRequest;
import com.example.propertyportal.dto.UserResponse;
import com.example.propertyportal.service.AuthService;
import com.example.propertyportal.dto.RegisterRequest;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(
            @Valid @RequestBody LoginRequest request) {

        UserResponse response = authService.login(request);

        return ResponseEntity.ok(response);
    }
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse response = authService.register(request);

        return ResponseEntity.status(201).body(response);
    }
}