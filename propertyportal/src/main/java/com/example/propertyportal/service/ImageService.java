
package com.example.propertyportal.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageService {

    public String saveImage(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Please select an image");
        }


        String contentType = file.getContentType();

        String extension;

        if ("image/jpeg".equals(contentType)) {
            extension = ".jpg";
        } else if ("image/png".equals(contentType)) {
            extension = ".png";
        } else if ("image/webp".equals(contentType)) {
            extension = ".webp";
        } else {
            throw new IllegalArgumentException("Only JPG, PNG and WEBP images are allowed");
        }


        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalArgumentException("Image size must be less than 5 MB");
        }

        try {
            // Create uploads folder
            Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();

            Files.createDirectories(uploadPath);

            // Generate a unique image name
            String fileName = UUID.randomUUID().toString() + extension;

            // Save image inside uploads folder
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath,
                    StandardCopyOption.REPLACE_EXISTING);

            // Return image URL
            return "/uploads/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Could not save image", e);
        }
    }
}
