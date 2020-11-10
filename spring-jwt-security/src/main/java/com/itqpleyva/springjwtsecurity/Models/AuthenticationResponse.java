package com.itqpleyva.springjwtsecurity.Models;

public class AuthenticationResponse {

    private String jwt;


    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return this.jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "{" +
            " jwt='" + getJwt() + "'" +
            "}";
    }

}