package com.example.fitness.service;

import com.example.fitness.model.User;
import com.example.fitness.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class UsersService {

    private final UserRepository userRepository;

    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            log.info("Username not found");
            return null;
        }
        if (!user.getPassword().equals(password)) {
            log.info("Incorrect password for user: " + username);
            return null;
        }
        return user;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

}
