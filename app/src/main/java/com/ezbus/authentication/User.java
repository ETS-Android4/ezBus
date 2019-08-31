package com.ezbus.authentication;

/**
 * Classe astratta che contiene comportamenti e informazioni dell'utente.
 */

abstract class User {

    private String id;
    String name;
    String email;


    public String getId() {
        return this.id;
    }

    void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

}