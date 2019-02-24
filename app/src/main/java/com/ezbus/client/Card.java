package com.ezbus.client;

import com.ezbus.R.drawable;

import java.util.UUID;

/**
 * Classe che descrive l'oggetto Card e il suo comportamento.
 * La tessera è un particolare titolo di viaggio che consente di abbonarsi ad una specifica tratta.
 */
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