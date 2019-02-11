package com.ezbus.client;

import java.util.Date;

public class Pass extends Item {

    private String name;
    private String companyId;
    private String type;
    private String city;
    private String price;



    public Pass(String companyId, String name, String type, String city, String price /*Date date*/) {
        this.name = name;
        super.id = generateId();
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