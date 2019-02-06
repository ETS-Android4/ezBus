package com.ezbus.authentication;

import com.ezbus.client.Pocket;

public class Client extends User {

    private String surname;
    private String age;
    private String username;
    private Pocket myPocket;


    public Client(String name, String surname, String age, String email, String username, Pocket pocket) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.username = username;
        this.myPocket = pocket;
    }

    public String getSurname() {
        return surname;
    }

    public String getAge() {
        return age;
    }

    public String getUsername() {
        return username;
    }

    public Pocket getMyPocket() {
        return myPocket;
    }
}