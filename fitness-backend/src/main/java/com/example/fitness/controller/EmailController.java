package com.example.fitness.controller;

import com.example.fitness.model.CredentialsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private JavaMailSender javaMailSender;

    @PostMapping("/send-credentials")
    public ResponseEntity<String> sendCredentials(@RequestBody CredentialsRequest credentialsRequest) {
        try {
            sendEmail(credentialsRequest.getEmail(), credentialsRequest.getUsername(), credentialsRequest.getPassword());
            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email");
        }
    }

    private void sendEmail(String to, String username, String password) {
        String subject = "Your Account Credentials";
        String text = String.format("Here are your credentials:\nUsername: %s\nPassword: %s", username, password);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("your-email@gmail.com");

        javaMailSender.send(message);
    }
}
