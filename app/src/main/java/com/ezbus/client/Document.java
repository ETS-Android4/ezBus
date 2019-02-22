package com.ezbus.client;

import java.io.Serializable;

abstract class Document implements Serializable {

    String id;
    String companyId;
    String name;
    double price;
    int expiration;


    public String getId() {
        return this.id;
    }

    String getCompanyId() {
        return this.companyId;
    }

    public String getName() {
        return this.name;
    }

    double getPrice() {
        return this.price;
    }

    int getExpiration() {
        return this.expiration;
    }

    abstract int getImage();

}