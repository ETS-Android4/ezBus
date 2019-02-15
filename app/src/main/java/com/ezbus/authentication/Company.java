package com.ezbus.authentication;

import com.ezbus.management.Route;

import java.util.ArrayList;
import java.util.List;

class Company extends User {

    private String iva;
    private List<Route> routes;


    public Company() { }

    Company(String name, String iva, String email) {
        this.name = name;
        this.email = email;
        this.iva = iva;
        this.routes = new ArrayList<>();
    }

    public String getIva() {
        return this.iva;
    }

    public List<Route> getRoutes() {
        return routes;
    }
}