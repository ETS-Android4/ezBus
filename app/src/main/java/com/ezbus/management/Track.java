package com.ezbus.management;

import java.sql.Time;
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
        this.stops = new ArrayList<Stop>();
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public void addStop(Stop s) {
        stops.add(s);
    }

    public String getId() {
        return id;
    }

    public List<Stop> getStops() {
        return stops;
    }
}
