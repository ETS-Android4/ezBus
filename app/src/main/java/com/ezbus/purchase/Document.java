package com.ezbus.purchase;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Classe astratta che contiene comportamenti e informazioni dei titoli di viaggio.
 */

abstract class Document implements Serializable {

    String id;
    String idCompany;
    String name;
    double price;
    Date expiration;


    public String getId() {
        return this.id;
    }

    public String getIdCompany() {
        return this.idCompany;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public Date getExpiration() {
        return this.expiration;
    }

    void calculateExpiration(int validity) {
        Calendar c = new GregorianCalendar();
        c.add(Calendar.DATE, validity);
        Date expiration = c.getTime();
        this.expiration = expiration;
    }

    boolean isValid() {
        if (getExpiration().after(new Date())) return true;
        return false;
    }

    abstract int giveImage();

}