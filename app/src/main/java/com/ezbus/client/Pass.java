package com.ezbus.client;


import java.util.Date;
import java.util.UUID;

public class Pass extends Item {

    private String name;
    private String companyId;
    private String type;
    private String city;
    private double price;
    private String id;



    public Pass(String companyId, String name, String city, String type, double price /*Date date*/) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.companyId = companyId;
        this.price = price;
        //super.expiration = date;
        this.type = type;
        this.city = city;
    }

    public String getName() { return name; }

    public String getCompanyId() { return companyId;}

    public String getType() {
        return type;
    }

    public String getCity() {
        return city;
    }

}