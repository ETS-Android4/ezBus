package com.ezbus.authentication;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

class Company extends User {

    private String iva;


    Company() { }

    Company(String uid, String name, String iva, String email) {
        this.setUid(uid);
        this.name = name;
        this.email = email;
        this.iva = iva;
        databaseSync();
    }

    public String getIva() {
        return this.iva;
    }

    @Override
    public void databaseSync() {
        DatabaseReference path = FirebaseDatabase.getInstance().getReference("/companies/"+this.getUid());
        path.setValue(this);
    }

}