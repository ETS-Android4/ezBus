package com.ezbus.client;

import java.io.Serializable;

class Document implements Serializable {

    String id;
    String companyId;
    String name;
    double price;
    int expiration;


    public String getId() {
        return this.id;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public int getExpiration() {
        return this.expiration;
    }

}