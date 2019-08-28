package com.ezbus.purchase;

import com.ezbus.R.drawable;

import java.util.UUID;

/**
 * Classe che descrive l'oggetto Pass e il suo comportamento.
 * L'abbonamento è un titolo di viaggio che consente di percorrere più tratte di un determinato territorio.
 */

public class Pass extends Document {

    private String city;
    private int validity;


    public Pass() {}

    public Pass(String companyId, double price, int validity, String name, String city) {
        this.id = UUID.randomUUID().toString();
        this.companyId = companyId;
        this.name = name;
        this.price = price;
        this.validity = validity;
        calculateExpiration(3);
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public int getValidity() {
        return this.validity;
    }

    int giveImage() {
        return drawable.pass;
    }

}