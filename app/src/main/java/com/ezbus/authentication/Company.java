package com.ezbus.authentication;

public class Company extends User {

    private String iva;
    private String username;

    public Company(String name, String iva, String username, String email) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.iva = iva;
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