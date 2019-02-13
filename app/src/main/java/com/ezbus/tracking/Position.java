package com.ezbus.tracking;

import android.location.Address;

public class Position {

    private double coordX;
    private double coordY;
    private Address address;

    public Position(double coordX, double coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public Position() {

    }

    public double getCoordX() {
        return coordX;
    }

    public double getCoordY() {
        return coordY;
    }

    public Address getAddress() {
        return address;
    }

}
