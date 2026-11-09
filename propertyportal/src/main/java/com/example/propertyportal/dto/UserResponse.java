package com.example.propertyportal.dto;
import com.example.propertyportal.entity.Role;
import java.time.LocalDateTime;

public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Role role;
    private LocalDateTime createdAt;

    public UserResponse(Long id, String fullName, String email,
                        String phoneNumber, Role role,
                        LocalDateTime createdAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
