package com.ezbus.authentication;

import com.ezbus.management.Route;

import java.util.ArrayList;
import java.util.List;

public class Company extends User {

    private String iva;
    private String username;
    private List<Route> routes;


    public Company(String name, String iva, String username, String email) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.iva = iva;
        routes = new ArrayList<Route>();
    }

    public String getIva() {
        return this.iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}