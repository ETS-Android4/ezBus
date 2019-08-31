package com.ezbus.management;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Classe che descrive l'oggetto Route e il suo comportamento.
 * Una tratta è costituita da una o più corse con differenti orari.
 */

public class Route {

    private String id;
    private String idCompany;
    private String name;
    private String idStartStop;
    private String idEndStop;
    private List<Track> tracks;


    public Route() { }

    Route (String idCompany, String name, String idStartStop, String idEndStop) {
        this.id = UUID.randomUUID().toString();
        this.idCompany = idCompany;
        this.name = name;
        this.idStartStop = idStartStop;
        this.idEndStop = idEndStop;
        this.tracks  = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getIdCompany() {
        return idCompany;
    }

    public String getName() {
        return name;
    }

    public String getStart() {
        return this.idStartStop;
    }

    public String getEnd() {
        return this.idEndStop;
    }

    public List<Track> getTracks() {
        return tracks;
    }

}