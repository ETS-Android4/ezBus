package com.ezbus.account;

import com.ezbus.client.Pocket;

class User {

    private String name;
    private String surname;
    private String age;
    private String email;
    private String username;
    private Pocket myPocket;


    public User(String name, String surname, String age, String email, String username, Pocket pocket) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.username = username;
        this.myPocket = pocket;
    }

}