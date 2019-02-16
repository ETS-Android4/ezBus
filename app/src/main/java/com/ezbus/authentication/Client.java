package com.ezbus.authentication;

import com.ezbus.client.Pocket;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Client extends User {

    private String surname;
    private Pocket myPocket;


    Client() { }

    Client(String uid, String name, String surname, String email, Pocket pocket) {
        this.setUid(uid);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.myPocket = pocket;
        databaseSync();
    }

    public String getSurname() {
        return surname;
    }

    public Pocket getMyPocket() {
        return myPocket;
    }

    @Override
    public void databaseSync() {
        DatabaseReference path = FirebaseDatabase.getInstance().getReference("/clients/"+this.getUid());
        path.setValue(this);
    }

}