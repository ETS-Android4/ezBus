package com.ezbus.client;

import com.ezbus.management.Stop;

import java.util.UUID;

class Ticket extends Item {

    private Stop start;
    private Stop end;


    public Ticket() {}

    public Ticket(String companyId, double price, int expiration, Stop start, Stop end) {
        this.id = UUID.randomUUID().toString();
        this.companyId = companyId;
        this.price = price;
        this.expiration = expiration;
        this.start = start;
        this.end = end;
    }

    public Stop getStart() {
        return this.start;
    }

    public Stop getEnd() {
        return this.end;
    }

}