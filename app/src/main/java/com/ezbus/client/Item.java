package com.ezbus.client;

import java.util.Date;

class Item {

    protected String id;
    protected String companyId;
    double price;
    Date expiration;


    public boolean isValid() {
        return true;
    }

    public String getId() {
        return this.id;
    }

    public String getCompanyId() {
        return this.companyId;
    }

    public double getPrice() {
        return this.price;
    }

    public Date getExpiration() {
        return this.expiration;
    }

}