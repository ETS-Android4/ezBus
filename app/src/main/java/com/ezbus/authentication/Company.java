package com.ezbus.authentication;

import com.ezbus.management.Route;

import java.util.ArrayList;
import java.util.List;

class Company extends User {

    private String iva;
    private String username;
    private List<Route> routes;


    public Company() { }

    Company(String name, String iva, String username, String email) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.iva = iva;
        this.routes = new ArrayList<>();
    }

    public String getIva() {
        return this.iva;
    }

    public String getUsername() {
        return username;
    }

    public List<Route> getRoutes() {
        return routes;
    }
}