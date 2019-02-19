package com.ezbus.client;

import java.util.UUID;

class Ticket extends Item {

    private String start;
    private String end;
    private int number;


    public Ticket() {
    }

    public Ticket(String companyId, String start, String end) {
        this.id = UUID.randomUUID().toString();
        this.companyId = companyId;
        //Per adesso impostiamo un prezzo fisso
        this.price = 5;
        this.expiration = 30;
        this.start = start;
        this.end = end;
        this.number = 1;
    }

    public String getStart() {
        return this.start;
    }

    public String getEnd() {
        return this.end;
    }

    public int getNumber() {
        return this.number;
    }

}
