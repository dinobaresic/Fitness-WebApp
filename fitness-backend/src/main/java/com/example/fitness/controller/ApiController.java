package com.example.fitness.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping("/api/something")
    public String getData() {
        return "Hello from Spring Boot backend!";
    }

    @GetMapping("/api/login")
    public String getSomethingElse() {
        return "Welcome to the login page!";
    }

}
