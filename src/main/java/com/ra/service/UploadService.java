package com.ra.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UploadService {
    private final Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        if (originalFilename != null && originalFilename.contains(".")) {
            originalFilename = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        }

        Map uploadPagrams = ObjectUtils.asMap(
                "public_id", originalFilename
        );

        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadPagrams);
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
