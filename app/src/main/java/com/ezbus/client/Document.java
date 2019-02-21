package com.ezbus.client;

class Document {

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