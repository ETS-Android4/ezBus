package com.ezbus.authentication;

import com.ezbus.client.Pocket;

public class User  extends Client {

    private String name;
    private String surname;
    private int age;
    private String email;
    private String username;
    private long id;
    private Pocket myPocket;


    public User(String name, String surname, int age, String email, String username,long id, Pocket pocket) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.email = email;
        this.username = username;
        this.id = id;
        this.myPocket = pocket;
    }

}