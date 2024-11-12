package com.example.fitness.model;

public class CredentialsRequest {

    private String email;
    private String username;
    private String password;


    public CredentialsRequest() {
    }

    public CredentialsRequest(String email, String username, String password) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
