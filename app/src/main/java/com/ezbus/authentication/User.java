package com.ezbus.authentication;

public abstract class User {

    private String uid;
    protected String name;
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

    public void setName(String name) {
        this.name = name;
    }

    String getEmail() {
        return this.email;
    }

    abstract void databaseSync();

}