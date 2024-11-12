package com.example.fitness.controller;

import com.example.fitness.model.User;
import com.example.fitness.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginUser) {
        String username = loginUser.getUsername();
        String password = loginUser.getPassword();

        if (userService.authenticate(username, password)) {
            return ResponseEntity.ok("Login successful");  // Return success message

        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Login failed");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody User newUser) {
        String username = newUser.getUsername();

        if (userService.getUserByUsername(username) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");  // Handle duplicate username
        } else if(userService.getUserByEmail(newUser.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists");  // Handle duplicate email
        }

        else {
            userService.save(newUser);
            return ResponseEntity.ok("User registered");
        }
    }

}
