package com.ezbus.client;

import com.ezbus.R.drawable;

import java.util.UUID;

public class Pass extends Document {

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

    String getCity() {
        return this.city;
    }

    int getImage() {
        return drawable.pass;
    }

}