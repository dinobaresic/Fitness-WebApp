package com.example.fitness.service;

import com.example.fitness.model.User;
import com.example.fitness.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);

        System.out.println("Login attempt with username: " + username); // Log login attempt


        if (user == null) {
            System.out.println("Username not found"); // Log if username not found
            return false;
        }
        if (!user.getPassword().equals(password)) {
            System.out.println("Incorrect password for user: " + username); // Log incorrect password
        }

        System.out.println("Login successful for user: " + username); // Log successful login

        return user.getPassword().equals(password);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();  // Fetch all users from the database
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);  // Fetch user by username
    }

    public void save(User newUser) {
         userRepository.save(newUser);  // Save new user to the database
    }


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);  // Fetch user by email
    }
}
