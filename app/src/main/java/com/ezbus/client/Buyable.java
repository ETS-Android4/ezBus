package com.ezbus.client;

interface Buyable {

    boolean isValid();
    void addToPocket();
    void generateQR();

}