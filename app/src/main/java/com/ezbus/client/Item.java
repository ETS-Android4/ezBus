package com.ezbus.client;

import java.util.Date;

class Item implements Buyable {

    protected int id;
    protected double price;
    protected Date expiration;


    int generateId() {
        return 0;
    }

    void disableItem() {

    }

    public void generateQR() {

    }

    public boolean isValid() {
        return true;
    }

    public void addToPocket() {
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public Date getExpiration() {
        return expiration;
    }

}