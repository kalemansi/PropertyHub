
package com.example.propertyportal.service;

import com.example.propertyportal.dto.PropertyRequest;
import com.example.propertyportal.dto.PropertyResponse;
import com.example.propertyportal.entity.Property;
import com.example.propertyportal.entity.User;
import com.example.propertyportal.repository.PropertyRepository;
import com.example.propertyportal.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    // Constructor Injection
    public PropertyService(PropertyRepository propertyRepository,
                           UserRepository userRepository,
                           ImageService imageService) {

        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
        this.imageService = imageService;
    }

    // 1. Create Property
    public PropertyResponse createProperty(PropertyRequest request) {

        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Property property = new Property();

        property.setTitle(request.getTitle());
        property.setDescription(request.getDescription());
        property.setCity(request.getCity());
        property.setLocality(request.getLocality());
        property.setPropertyType(request.getPropertyType());
        property.setListingType(request.getListingType());
        property.setPrice(request.getPrice());
        property.setBedrooms(request.getBedrooms());
        property.setBathrooms(request.getBathrooms());
        property.setArea(request.getArea());

        property.setOwner(owner);

        Property savedProperty = propertyRepository.save(property);

        return convertToResponse(savedProperty);
    }

    // 2. Get All Properties
    public List<PropertyResponse> getAllProperties() {

        List<Property> properties = propertyRepository.findAll();

        List<PropertyResponse> responses = new ArrayList<>();

        for (Property property : properties) {
            responses.add(convertToResponse(property));
        }

        return responses;
    }

    // 3. Get Property By ID
    public PropertyResponse getPropertyById(Long id) {

        Property property = propertyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Property not found with ID: " + id));

        return convertToResponse(property);
    }

    // 4. Convert Property Entity into PropertyResponse
    private PropertyResponse convertToResponse(Property property) {

        return new PropertyResponse(
                property.getId(),
                property.getTitle(),
                property.getDescription(),
                property.getCity(),
                property.getLocality(),
                property.getPropertyType(),
                property.getListingType(),
                property.getPrice(),
                property.getBedrooms(),
                property.getBathrooms(),
                property.getArea(),
                property.getApprovalStatus(),
                property.getImageUrl(),
                property.getOwner().getId(),
                property.getOwner().getFullName(),
                property.getOwner().getPhoneNumber(),
                property.getCreatedAt()
        );
    }


    public PropertyResponse updateProperty(Long id, PropertyRequest request) {

        Property property = propertyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Property not found with ID: " + id));

        property.setTitle(request.getTitle());
        property.setDescription(request.getDescription());
        property.setCity(request.getCity());
        property.setLocality(request.getLocality());
        property.setPropertyType(request.getPropertyType());
        property.setListingType(request.getListingType());
        property.setPrice(request.getPrice());
        property.setBedrooms(request.getBedrooms());
        property.setBathrooms(request.getBathrooms());
        property.setArea(request.getArea());

        Property updatedProperty = propertyRepository.save(property);

        return convertToResponse(updatedProperty);
    }


    public void deleteProperty(Long id) {

        Property property = propertyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Property not found with ID: " + id));

        propertyRepository.delete(property);
    }


    public List<PropertyResponse> getPendingProperties() {

        List<Property> properties =
                propertyRepository.findByApprovalStatus("PENDING");

        List<PropertyResponse> responses = new ArrayList<>();

        for (Property property : properties) {
            responses.add(convertToResponse(property));
        }

        return responses;
    }


    public PropertyResponse approveProperty(Long id) {

        Property property = propertyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Property not found"));

        property.setApprovalStatus("APPROVED");

        Property savedProperty = propertyRepository.save(property);

        return convertToResponse(savedProperty);
    }


    public PropertyResponse rejectProperty(Long id) {

        Property property = propertyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Property not found"));

        property.setApprovalStatus("REJECTED");

        Property savedProperty = propertyRepository.save(property);

        return convertToResponse(savedProperty);
    }


    public List<PropertyResponse> getApprovedProperties() {

        List<Property> properties =
                propertyRepository.findByApprovalStatus("APPROVED");

        List<PropertyResponse> responses = new ArrayList<>();

        for (Property property : properties) {
            responses.add(convertToResponse(property));
        }

        return responses;
    }


    public List<PropertyResponse> getRejectedProperties() {

        List<Property> properties =
                propertyRepository.findByApprovalStatus("REJECTED");

        List<PropertyResponse> responses = new ArrayList<>();

        for (Property property : properties) {
            responses.add(convertToResponse(property));
        }

        return responses;
    }


    public List<PropertyResponse> searchApprovedProperties(
            String city, String locality) {

        List<Property> properties;

        if ((city == null || city.trim().isEmpty()) &&
                (locality == null || locality.trim().isEmpty())) {

            properties = propertyRepository.findByApprovalStatus("APPROVED");
        }
        else if (locality == null || locality.trim().isEmpty()) {

            properties = propertyRepository
                    .findByApprovalStatusAndCityContainingIgnoreCase(
                            "APPROVED", city.trim());
        }
        else if (city == null || city.trim().isEmpty()) {

            properties = propertyRepository
                    .findByApprovalStatusAndLocalityContainingIgnoreCase(
                            "APPROVED", locality.trim());
        }
        else {

            properties = propertyRepository
                    .findByApprovalStatusAndCityContainingIgnoreCaseAndLocalityContainingIgnoreCase(
                            "APPROVED", city.trim(), locality.trim());
        }

        List<PropertyResponse> responses = new ArrayList<>();

        for (Property property : properties) {
            responses.add(convertToResponse(property));
        }

        return responses;
    }


    public PropertyResponse uploadPropertyImage(
            Long id, MultipartFile image) {

        Property property = propertyRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Property not found"));

        String imageUrl = imageService.saveImage(image);

        property.setImageUrl(imageUrl);

        Property updatedProperty = propertyRepository.save(property);

        return convertToResponse(updatedProperty);
    }


    public List<PropertyResponse> filterProperties(
            String city,
            String locality,
            String listingType,
            String propertyType,
            Double minPrice,
            Double maxPrice) {

        List<Property> properties =
                propertyRepository.findByApprovalStatus("APPROVED");

        List<PropertyResponse> responses = new ArrayList<>();

        for (Property property : properties) {

            boolean matches = true;

            if (city != null && !city.trim().isEmpty()) {
                if (!property.getCity()
                        .toLowerCase()
                        .contains(city.trim().toLowerCase())) {
                    matches = false;
                }
            }

            if (locality != null && !locality.trim().isEmpty()) {
                if (!property.getLocality()
                        .toLowerCase()
                        .contains(locality.trim().toLowerCase())) {
                    matches = false;
                }
            }

            if (listingType != null && !listingType.trim().isEmpty()) {
                if (!property.getListingType()
                        .equalsIgnoreCase(listingType.trim())) {
                    matches = false;
                }
            }

            if (propertyType != null && !propertyType.trim().isEmpty()) {
                if (!property.getPropertyType()
                        .equalsIgnoreCase(propertyType.trim())) {
                    matches = false;
                }
            }

            if (minPrice != null && property.getPrice() < minPrice) {
                matches = false;
            }

            if (maxPrice != null && property.getPrice() > maxPrice) {
                matches = false;
            }

            if (matches) {
                responses.add(convertToResponse(property));
            }
        }

        return responses;
    }


    public List<PropertyResponse> getPropertiesBySeller(Long ownerId) {

        List<Property> properties =
                propertyRepository.findByOwner_Id(ownerId);

        List<PropertyResponse> responses = new ArrayList<>();

        for (Property property : properties) {
            responses.add(convertToResponse(property));
        }

        return responses;
    }

}
