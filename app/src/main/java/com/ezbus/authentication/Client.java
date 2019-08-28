package com.ezbus.authentication;

import com.ezbus.purchase.Pocket;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Classe che descrive l'oggetto Client e il suo comportamento.
 * Il cliente è un particolare tipo di utente che utilizza i servizi offerti dalle aziende.
 */

public class Client extends User implements DataSync {

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

    public void setMyPocket(Pocket pocket) {
        this.myPocket = pocket;
    }

    public void databaseSync() {
        DatabaseReference path = FirebaseDatabase.getInstance().getReference("/clients/"+this.getUid());
        path.setValue(this);
    }

}