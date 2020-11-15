package com.itqpleyva.springjwtsecurity.Models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ACLModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String path;
    private String method;
    private String allowed_roles;
    

    public ACLModel(String path, String method, String roles_list) {
        this.path = path;
        this.method = method;
        this.allowed_roles = roles_list;
    }

    public ACLModel() {
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getAllowed_roles() {
        return this.allowed_roles;
    }

    public void setAllowed_roles(String allowed_roles) {
        this.allowed_roles = allowed_roles;
    }


    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", path='" + getPath() + "'" +
            ", method='" + getMethod() + "'" +
            ", allowed_roles='" + getAllowed_roles() + "'" +
            "}";
    }

}