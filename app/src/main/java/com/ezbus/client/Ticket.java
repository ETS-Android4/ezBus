package com.ezbus.client;

import java.util.Date;

class Ticket extends Item {

    private String start;
    private String destination;


    public Ticket() {}

    public Ticket(String start, String destination, double price, Date date) {
        this.id = generateId();
        this.price = price;
        this.expiration = date;
        this.start = start;
        this.destination = destination;
    }

    public String getStart() {
        return start;
    }

    public String getDestination() {
        return destination;
    }

}