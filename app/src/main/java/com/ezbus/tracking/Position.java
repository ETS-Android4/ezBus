package com.ezbus.tracking;

public class Position {

    private double coordX;
    private double coordY;

    public Position() {

    }

    public Position(double coordX, double coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public double getCoordX() {
        return coordX;
    }

    public double getCoordY() {
        return coordY;
    }

}
