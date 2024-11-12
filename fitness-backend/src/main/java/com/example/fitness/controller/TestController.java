package com.example.fitness.controller;


import com.example.fitness.model.User;
import com.example.fitness.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();  // Fetch all users for testing
    }

    @GetMapping("/user")
    public User getUserByUsername(@RequestParam String username) {
        return userService.getUserByUsername(username);  // Fetch user by username for testing
    }
}
