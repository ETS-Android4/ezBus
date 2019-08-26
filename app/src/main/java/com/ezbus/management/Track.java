package com.ezbus.management;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Classe che descrive l'oggetto Track e il suo comportamento.
 * Una corsa è il tragitto che un autobus percorre in un determinato orario.
 */

class Track {

    private String time;
    private String id;
    private List<Stop> stops;


    public Track(String time) {
        this.time = time;
        this.id = UUID.randomUUID().toString();
        this.stops = new ArrayList<>();
    }

    public String getTime() {
        return this.time;
    }

    public String getId() {
        return this.id;
    }

    public List<Stop> getStops() {
        return this.stops;
    }

}