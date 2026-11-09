
package com.example.propertyportal.repository;

import com.example.propertyportal.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    // Find properties by approval status
    List<Property> findByApprovalStatus(String approvalStatus);

    // Search approved properties by city
    List<Property> findByApprovalStatusAndCityContainingIgnoreCase(
            String approvalStatus, String city);

    // Search approved properties by locality
    List<Property> findByApprovalStatusAndLocalityContainingIgnoreCase(
            String approvalStatus, String locality);

    // Search approved properties by city and locality
    List<Property> findByApprovalStatusAndCityContainingIgnoreCaseAndLocalityContainingIgnoreCase(
            String approvalStatus, String city, String locality);

    // Filter approved properties by listing type
    List<Property> findByApprovalStatusAndListingType(
            String approvalStatus, String listingType);

    // Filter approved properties by property type
    List<Property> findByApprovalStatusAndPropertyType(
            String approvalStatus, String propertyType);

    // Filter approved properties by price range
    List<Property> findByApprovalStatusAndPriceBetween(
            String approvalStatus, Double minPrice, Double maxPrice);

    // Find properties belonging to a specific seller
    List<Property> findByOwner_Id(Long ownerId);
}
