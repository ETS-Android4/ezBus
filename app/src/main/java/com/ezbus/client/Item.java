package com.ezbus.client;

import java.util.Date;

class Item implements Buyable {

    protected String id;
    protected String companyId;
    protected double price;
    protected Date expiration;


    public void generateQR() {

    }

    public boolean isValid() {
        return true;
    }

    public void addToPocket() {

    }

    public String getId() {
        return id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public double getPrice() {
        return price;
    }

    public Date getExpiration() {
        return expiration;
    }

}