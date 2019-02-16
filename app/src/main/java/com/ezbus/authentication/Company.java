package com.ezbus.authentication;

import com.ezbus.management.Route;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

class Company extends User {

    private String iva;
    private List<Route> routes;


    Company() { }

    Company(String uid, String name, String iva, String email) {
        this.setUid(uid);
        this.name = name;
        this.email = email;
        this.iva = iva;
        this.routes = new ArrayList<>();
        databaseSync();
    }

    public String getIva() {
        return this.iva;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    @Override
    public void databaseSync() {
        DatabaseReference path = FirebaseDatabase.getInstance().getReference("/companies/"+this.getUid());
        path.setValue(this);
    }

}