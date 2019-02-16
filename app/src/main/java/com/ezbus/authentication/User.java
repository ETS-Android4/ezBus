package com.ezbus.authentication;

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

    String getName() {
        return this.name;
    }

    String getEmail() {
        return this.email;
    }

    abstract void databaseSync();

}