package com.ezbus.account;

interface Buyable {

    boolean isValid();
    void addToPocket();
    void generateQR();

}