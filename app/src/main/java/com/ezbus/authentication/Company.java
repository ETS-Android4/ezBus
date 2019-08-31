package com.ezbus.authentication;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Classe che descrive l'oggetto Company e il suo comportamento.
 * L'azienda è un particolare tipo di utente che fornisce determinati servizi per il cliente.
 */

class Company extends User implements DataSync {

    private String iva;


    Company() { }

    Company(String id, String name, String iva, String email) {
        this.setId(id);
        this.name = name;
        this.email = email;
        this.iva = iva;
        databaseSync();
    }

    public String getIva() {
        return this.iva;
    }

    public void databaseSync() {
        DatabaseReference path = FirebaseDatabase.getInstance().getReference("/companies/"+this.getId());
        path.setValue(this);
    }

}