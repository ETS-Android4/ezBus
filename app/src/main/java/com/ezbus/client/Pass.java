package com.ezbus.client;

import com.ezbus.R.drawable;

import java.util.UUID;

public class Pass extends Document {

    private String city;
    private int validity;


    public Pass() {}

    public Pass(String companyId, double price, int validity, String name, String city) {
        this.id = UUID.randomUUID().toString();
        this.companyId = companyId;
        this.name = name;
        this.price = price;
        this.validity = validity;
        calculateExpiration(3);
        this.city = city;
    }

    String getCity() {
        return this.city;
    }

    int getValidity() {
        return this.validity;
    }

    int giveImage() {
        return drawable.pass;
    }

}