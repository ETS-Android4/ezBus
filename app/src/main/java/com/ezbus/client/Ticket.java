package com.ezbus.client;

import com.ezbus.R.drawable;

import java.util.UUID;

class Ticket extends Document {

    private String start;
    private String end;
    private int number;


    public Ticket() {
    }

    public Ticket(String companyId, String start, String end, String name) {
        this.id = UUID.randomUUID().toString();
        this.companyId = companyId;
        this.name = name;
        this.price = 5; //Per ora prezzo fisso
        this.expiration = 30;
        this.start = start;
        this.end = end;
        this.number = 1;
    }

    String getStart() {
        return this.start;
    }

    String getEnd() {
        return this.end;
    }

    int getNumber() {
        return this.number;
    }

    void setNumber(int number) {
        this.number = number;
    }

    int getImage() {
        return drawable.ticket;
    }

}