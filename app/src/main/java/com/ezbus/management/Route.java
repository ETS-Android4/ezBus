package com.ezbus.management;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private String name;
    private String id;
    private String compName;
    private Track track;
    private Stop start;
    private Stop end;
    private List<Stop> stops = new ArrayList<Stop>();

    public Route(String name, String company) {
        this.name = name;
        this.compName = company;
        this.setStart();
        this.setEnd();
    }

    public Stop getStart() {
        return this.start;
    }

    public void setStart() {
        this.start = Track.addStop();
    }

    public Stop getEnd() {
        return this.end;
    }

    public void setEnd() {
        this.end = Track.addStop();
    }
}
