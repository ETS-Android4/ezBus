package com.ezbus.client;

import java.util.UUID;

public class Pass extends Item {

    private String name;
    private String city;


    public Pass() {}

    public Pass(String companyId, double price, int expiration, String name, String city) {
        this.id = UUID.randomUUID().toString();
        this.companyId = companyId;
        this.price = price;
        this.expiration = expiration;
        this.name = name;
        this.city = city;
    }

    public String getName() { return this.name; }

    public String getCity() {
        return this.city;
    }

}