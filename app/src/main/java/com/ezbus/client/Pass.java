package com.ezbus.client;

import java.util.Date;

public class Pass extends Item {

    private int type;
    private String city;


    public Pass(int type, String city, double price, Date date) {
        super.id = generateId();
        super.price = price;
        super.expiration = date;
        this.type = type;
        this.city = city;
    }

}