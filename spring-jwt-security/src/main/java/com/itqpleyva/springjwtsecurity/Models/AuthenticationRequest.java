package com.itqpleyva.springjwtsecurity.Models;

public class AuthenticationRequest {

    private String user;
    private String password;


    public AuthenticationRequest(String user, String password) {
        this.user = user;
        this.password = password;
    }

    @Override
    public String toString() {
        return "{" +
            " user='" + getUser() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }

    public String getUser() {
        return this.user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}