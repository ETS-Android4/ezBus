package com.ezbus.authentication;

/**
 * Classe astratta che contiene comportamenti e informazioni dell'utente.
 */

abstract class User {

    private String uid;
    String name;
    String email;


    public String getUid() {
        return this.uid;
    }

    void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

}