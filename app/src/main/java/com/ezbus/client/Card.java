package com.ezbus.client;

import java.util.Date;

class Card extends Item {

    private String start;
    private String destination;
    private int validity;


    public Card(String start, String destination, double price, Date date, int validity) {
        this.id = generateId();
        this.price = price;
        this.expiration = date;
        this.start = start;
        this.destination = destination;
        this.validity = validity;
    }

    public String getStart() {
        return start;
    }

    public String getDestination() {
        return destination;
    }

    public int getValidity() {
        return validity;
    }

}