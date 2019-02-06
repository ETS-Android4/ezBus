package com.ezbus.authentication;

import com.ezbus.client.Pocket;

public class Client extends User {

    private String surname;
    private Pocket myPocket;


    public Client(String name, String surname, String email, Pocket pocket) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.myPocket = pocket;
    }

    public String getSurname() {
        return surname;
    }

    public Pocket getMyPocket() {
        return myPocket;
    }

}