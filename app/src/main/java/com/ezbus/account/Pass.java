package com.ezbus.account;

import java.util.Date;

class Pass extends Item {

    private int type;
    private String city;


    public Pass(int type, String city, double price, Date date) {
        super.id = generateId();
        super.price = price;
        super.date = date;
        this.type = type;
        this.city = city;
    }

}