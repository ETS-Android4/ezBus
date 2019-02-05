package com.ezbus.authentication;

public class Company extends Client {

    private String name;
    private String email;
    private String iva;
    private long id;


    public Company(String name, String email, String iva) {
        this.name = name;
        this.email = email;
        this.iva = iva;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIva() {
        return this.iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

}