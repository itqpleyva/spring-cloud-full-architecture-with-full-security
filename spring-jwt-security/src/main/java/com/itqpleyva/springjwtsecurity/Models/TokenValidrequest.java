package com.itqpleyva.springjwtsecurity.Models;

public class TokenValidrequest {

    private String token;
    private String username;

    public TokenValidrequest() {
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}