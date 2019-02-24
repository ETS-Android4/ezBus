package com.ezbus.management;

import com.ezbus.tracking.Position;

import java.util.UUID;

/**
 * Classe che descrive l'oggetto Stop e il suo comportamento.
 * Una fermata è punto iniziale, intermedio o finale del tragitto percorso da un autobus.
 */

public class Stop {

    private String id;
    private String companyId;
    private Position position;
    private String name;


    Stop() { }

    Stop (String companyId, Position position, String name) {
        this.id = UUID.randomUUID().toString();
        this.companyId = companyId;
        this.position = position;
        this.name = name;
    }

    public Position getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getCompanyId() {
        return companyId;
    }

}