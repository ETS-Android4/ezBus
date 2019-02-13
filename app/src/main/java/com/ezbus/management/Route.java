package com.ezbus.management;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Route {

    private String name;
    private String id;
    private String companyId;
    private String idStartStop;
    private String idEndStop;
    private List<Track> tracks;

    public Route() {
        this.id = UUID.randomUUID().toString();
    }

    public Route(String name, String companyId, String idStartStop, String idEndStop) {
        this.name = name;
        this.id = UUID.randomUUID().toString();
        this.companyId = companyId;
        this.idStartStop = idStartStop;
        this.idEndStop = idEndStop;
        this.tracks  = new ArrayList<>();
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public String getStart() {
        return this.idStartStop;
    }

    public String getEnd() {
        return this.idEndStop;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
