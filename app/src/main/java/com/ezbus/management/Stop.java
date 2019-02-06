package com.ezbus.management;

import com.ezbus.tracking.Position;

import java.sql.Time;

public class Stop {

    private Position position;
    private Time time;
    private double km;

    public Stop(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
