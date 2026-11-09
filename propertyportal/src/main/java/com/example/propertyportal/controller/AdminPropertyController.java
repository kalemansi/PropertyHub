
package com.example.propertyportal.controller;

import com.example.propertyportal.dto.PropertyResponse;
import com.example.propertyportal.service.PropertyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/admin/properties")
public class AdminPropertyController {

    @Autowired
    private PropertyService propertyService;

    // Get all pending properties
    @GetMapping("/pending")
    public ResponseEntity<List<PropertyResponse>> getPendingProperties() {

        List<PropertyResponse> properties =
                propertyService.getPendingProperties();

        return ResponseEntity.ok(properties);
    }

    // Get all rejected properties
    @GetMapping("/rejected")
    public ResponseEntity<List<PropertyResponse>> getRejectedProperties() {

        List<PropertyResponse> properties =
                propertyService.getRejectedProperties();

        return ResponseEntity.ok(properties);
    }

    // Get all approved properties
    @GetMapping("/approved")
    public ResponseEntity<List<PropertyResponse>> getApprovedProperties() {

        List<PropertyResponse> properties =
                propertyService.getApprovedProperties();

        return ResponseEntity.ok(properties);
    }


    // Approve a property
    @PutMapping("/{id}/approve")
    public ResponseEntity<PropertyResponse> approveProperty(
            @PathVariable Long id) {

        PropertyResponse response =
                propertyService.approveProperty(id);

        return ResponseEntity.ok(response);
    }

    // Reject a property
    @PutMapping("/{id}/reject")
    public ResponseEntity<PropertyResponse> rejectProperty(
            @PathVariable Long id) {

        PropertyResponse response =
                propertyService.rejectProperty(id);

        return ResponseEntity.ok(response);
    }
}
