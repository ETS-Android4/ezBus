package com.ezbus.management;

import com.ezbus.tracking.Position;

import java.sql.Time;
import java.util.UUID;

public class Stop {

    private Position position;
    private String name;
    private String id;
    private String companyId;
    private double km;

    public Stop() {
        this.id = UUID.randomUUID().toString();
    }


    public Stop(String name, Position position, String companyId) {
        this.position = position;
        this.id = UUID.randomUUID().toString();
        this.companyId = companyId;
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