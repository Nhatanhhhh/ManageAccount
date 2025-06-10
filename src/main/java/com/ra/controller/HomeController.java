package com.ra.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@Tag(name = "Home", description = "Controller of Home page")
public class HomeController {
    @GetMapping("/home")
    public ResponseEntity<?> home() {
        return new ResponseEntity<>("Home Page", HttpStatus.OK);
    }

//    @GetMapping("/cart")
//    public ResponseEntity<?> cart() {
//        return new ResponseEntity<>("Cart Page", HttpStatus.OK);
//    }
}
