package com.ezbus.management;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Track {

    private String time;
    private String name;
    private List<Stop> stops;
    private String id;


    public Track(String name, String time) {
        this.name = name;
        this.time = time;
        this.id = UUID.randomUUID().toString();
        this.stops = new ArrayList<>();
    }

    public String getTime() {
        return this.time;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public List<Stop> getStops() {
        return this.stops;
    }

}