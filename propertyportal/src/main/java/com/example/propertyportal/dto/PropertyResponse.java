
package com.example.propertyportal.dto;

import java.time.LocalDateTime;

public class PropertyResponse {

    private Long id;
    private String title;
    private String description;
    private String city;
    private String locality;
    private String propertyType;
    private String listingType;
    private Double price;
    private Integer bedrooms;
    private Integer bathrooms;
    private Double area;
    private String approvalStatus;
    private String imageUrl;
    private Long ownerId;
    private String ownerName;
    private String ownerPhone;
    private LocalDateTime createdAt;

    public PropertyResponse() {
    }

    public PropertyResponse(Long id, String title, String description,
                            String city, String locality, String propertyType,
                            String listingType, Double price, Integer bedrooms,
                            Integer bathrooms, Double area, String approvalStatus,
                            String imageUrl, Long ownerId, String ownerName,String ownerPhone,
                            LocalDateTime createdAt) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.city = city;
        this.locality = locality;
        this.propertyType = propertyType;
        this.listingType = listingType;
        this.price = price;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.area = area;
        this.approvalStatus = approvalStatus;
        this.imageUrl = imageUrl;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCity() {
        return city;
    }

    public String getLocality() {
        return locality;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public String getListingType() {
        return listingType;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public Double getArea() {
        return area;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
