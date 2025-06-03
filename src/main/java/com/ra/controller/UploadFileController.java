package com.ra.controller;

import com.ra.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/upload")
public class UploadFileController {
    @Autowired
    private UploadService uploadService;

    @PostMapping
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) {
        String fileName = uploadService.uploadFile(file);
        return new ResponseEntity<>(fileName,HttpStatus.CREATED);
    }
}
