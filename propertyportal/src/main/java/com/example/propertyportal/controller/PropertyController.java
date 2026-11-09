
package com.example.propertyportal.controller;

import com.example.propertyportal.dto.PropertyRequest;
import com.example.propertyportal.dto.PropertyResponse;
import com.example.propertyportal.service.PropertyService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyService propertyService;

    // Constructor Injection
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    // Create a new property
    @PostMapping
    public ResponseEntity<PropertyResponse> createProperty(
            @RequestBody PropertyRequest request) {

        PropertyResponse response =
                propertyService.createProperty(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Get All Properties
    @GetMapping
    public ResponseEntity<List<PropertyResponse>> getAllProperties() {

        List<PropertyResponse> properties =
                propertyService.getAllProperties();

        return ResponseEntity.ok(properties);
    }

    // Get Property By ID
    @GetMapping("/{id}")
    public ResponseEntity<PropertyResponse> getPropertyById(
            @PathVariable Long id) {

        PropertyResponse property =
                propertyService.getPropertyById(id);

        return ResponseEntity.ok(property);
    }

    // Update Property
    @PutMapping("/{id}")
    public ResponseEntity<PropertyResponse> updateProperty(
            @PathVariable Long id,
            @RequestBody PropertyRequest request) {

        PropertyResponse updatedProperty =
                propertyService.updateProperty(id, request);

        return ResponseEntity.ok(updatedProperty);
    }

    // Delete Property
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(
            @PathVariable Long id) {

        propertyService.deleteProperty(id);

        return ResponseEntity.noContent().build();
    }

    // Get all approved properties
    @GetMapping("/approved")
    public ResponseEntity<List<PropertyResponse>> getApprovedProperties() {

        List<PropertyResponse> properties =
                propertyService.getApprovedProperties();

        return ResponseEntity.ok(properties);
    }


    // Search approved properties by city and locality
    @GetMapping("/search")
    public ResponseEntity<List<PropertyResponse>> searchProperties(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String locality) {

        List<PropertyResponse> properties =
                propertyService.searchApprovedProperties(city, locality);

        return ResponseEntity.ok(properties);
    }


    // Filter approved properties
    @GetMapping("/filter")
    public ResponseEntity<List<PropertyResponse>> filterProperties(

            @RequestParam(required = false) String city,
            @RequestParam(required = false) String locality,
            @RequestParam(required = false) String listingType,
            @RequestParam(required = false) String propertyType,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        List<PropertyResponse> properties =
                propertyService.filterProperties(
                        city,
                        locality,
                        listingType,
                        propertyType,
                        minPrice,
                        maxPrice
                );

        return ResponseEntity.ok(properties);
    }



    // Upload property image
    @PostMapping(
            value = "/{id}/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<PropertyResponse> uploadPropertyImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image) {

        PropertyResponse response =
                propertyService.uploadPropertyImage(id, image);

        return ResponseEntity.ok(response);
    }

    // Get properties belonging to a specific seller
    @GetMapping("/seller/{ownerId}")
    public ResponseEntity<List<PropertyResponse>> getPropertiesBySeller(
            @PathVariable Long ownerId) {

        List<PropertyResponse> properties =
                propertyService.getPropertiesBySeller(ownerId);

        return ResponseEntity.ok(properties);
    }
}
