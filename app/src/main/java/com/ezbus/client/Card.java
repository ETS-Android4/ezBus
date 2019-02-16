package com.ezbus.client;

import com.ezbus.management.Route;

import java.util.UUID;

class Card extends Item {

    private Route route;


    public Card() {}

    public Card(String companyId, double price, int expiration, Route route) {
        this.id = UUID.randomUUID().toString();
        this.companyId = companyId;
        this.price = price;
        this.expiration = expiration;
        this.route = route;
    }

    public Route getRoute() { return this.route; }

}