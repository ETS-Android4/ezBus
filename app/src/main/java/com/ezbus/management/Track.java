package com.ezbus.management;

import com.ezbus.tracking.Position;

import java.sql.Time;

public class Track {

    private Time start;
    private Time delay;
    private Stop stops;

    public Track(Time start) {
        this.setStart(start);
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public static Stop addStop() {

        //Position newPosition = new Position(coordX, coordY);
        //Stop newStop = new Stop(newPosition);

        //return newStop;
        return null;
    }

    public static Stop editStop() {

        return null;
    }
}
