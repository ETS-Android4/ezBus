package com.ezbus.client;

import java.util.UUID;

class Card extends Document {

    private String idRoute;


    public Card() {}

    public Card(String companyId, int expiration, String idRoute) {
        this.id = UUID.randomUUID().toString();
        this.companyId = companyId;
        this.price = 20; //Per ora prezzo fisso
        this.expiration = expiration;
        this.idRoute = idRoute;
    }

    public String getIdRoute() { return this.idRoute; }

}