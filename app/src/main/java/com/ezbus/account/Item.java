package com.ezbus.account;

import java.util.Date;

abstract class Item implements Buyable {

    protected int id;
    protected double price;
    protected Date date;


    int generateId() {
        return 0;
    }

    void disableItem() {

    }

    void generateQR() {

    }

    public boolean isValid() {
        return true;
    }

}