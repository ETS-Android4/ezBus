package com.ezbus.client;

import java.util.Date;

class Card extends Item {

    private String start;
    private String destination;
    private int validity;


    public Card(String start, String destination, double price, Date date, int validity) {
        super.id = generateId();
        super.price = price;
        super.expiration = date;
        this.start = start;
        this.destination = destination;
        this.validity = validity;
    }

}