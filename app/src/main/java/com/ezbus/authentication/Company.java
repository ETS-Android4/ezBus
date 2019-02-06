package com.ezbus.authentication;

public class Company extends User {

    private String name;
    private String email;
    private String iva;


    public Company(String name, String email, String iva) {
        this.name = name;
        this.email = email;
        this.iva = iva;
    }

    public String getIva() {
        return this.iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

}