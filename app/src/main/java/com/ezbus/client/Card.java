package com.ezbus.client;

import com.ezbus.R.drawable;

import java.util.UUID;

class Card extends Document {

    private String routeId;


    public Card() {}

    public Card(String companyId, int validity, String routeId, String routeName) {
        this.id = UUID.randomUUID().toString();
        this.companyId = companyId;
        this.name = routeName;
        this.price = 20; //Per ora prezzo fisso
        calculateExpiration(validity);
        this.routeId = routeId;
    }

    String getRouteId() {
        return this.routeId;
    }

    int giveImage() {
        return drawable.card;
    }

}