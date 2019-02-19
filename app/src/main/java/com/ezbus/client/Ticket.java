package com.ezbus.client;

import java.util.UUID;

class Ticket extends Item {

    private String start;
    private String end;


    public Ticket() {}

    public Ticket(String companyId, double price, int expiration, String start, String end) {
        this.id = UUID.randomUUID().toString();
        this.companyId = companyId;
        this.price = price;
        this.expiration = expiration;
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return this.start;
    }

    public String getEnd() {
        return this.end;
    }

}