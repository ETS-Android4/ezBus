package com.ezbus.client;


import java.util.Date;
import java.util.UUID;

public class Pass extends Item {

    private String name;
    private String city;
    private String type;

    public Pass() {
        this.id = UUID.randomUUID().toString();
    }


    public Pass(String companyId, String name, String city, String type, double price) {
        this.id = UUID.randomUUID().toString();
        this.companyId = companyId;
        this.name = name;
        this.city = city;
        this.type = type;
        this.price = price;
    }

    public String getName() { return name; }

    public String getType() {
        return type;
    }

    public String getCity() {
        return city;
    }

}