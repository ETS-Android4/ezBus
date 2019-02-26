package com.ezbus.purchase;

import com.ezbus.R.drawable;

import java.util.UUID;

/**
 * Classe che descrive l'oggetto Ticket e il suo comportamento.
 * Il biglietto è un titolo di viaggio che consente di percorrere una determinata tratta.
 */

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
        calculateExpiration(3);
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

    int giveImage() {
        return drawable.ticket;
    }

}