package com.ezbus.account;

import java.util.Date;

class Ticket extends Item {

    private String start;
    private String destination;


    public Ticket(String start, String destination, double price, Date date) {
        super.id = generateId();
        super.price = price;
        super.date = date;
        this.start = start;
        this.destination = destination;
    }

}