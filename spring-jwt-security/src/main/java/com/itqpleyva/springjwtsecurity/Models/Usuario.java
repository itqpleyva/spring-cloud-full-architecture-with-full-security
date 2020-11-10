package com.itqpleyva.springjwtsecurity.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Usuario {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
private String username;
private String password;
private String roles;


    public Usuario() {
    }

    public Usuario( String username, String password, String roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return this.roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

}
