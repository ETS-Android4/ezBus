package com.ezbus.client;

import com.ezbus.R.drawable;

import java.util.UUID;

class Card extends Document {

    private String routeId;


    public Card() {}

    public Card(String companyId, int expiration, String routeId, String routeName) {
        this.id = UUID.randomUUID().toString();
        this.companyId = companyId;
        this.name = routeName;
        this.price = 20; //Per ora prezzo fisso
        this.expiration = expiration;
        this.routeId = routeId;
    }

    public String getRouteId() {
        return this.routeId;
    }

    int getImage() {
        return drawable.card;
    }

}