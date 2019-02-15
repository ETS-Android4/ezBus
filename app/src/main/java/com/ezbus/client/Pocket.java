package com.ezbus.client;

import com.ezbus.authentication.ProfileActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pocket {

    private double credit;
    private List<Ticket> myTickets = new ArrayList<>();
    private List<Card> myCards = new ArrayList<>();
    private List<Pass> myPasses = new ArrayList<>();


    public Pocket() {
        this.credit = 0;
        this.addCard(new Card("Ciao", "caro", 1.0, new Date(), 3));
        /*this.addTicket(new Ticket("Ciao", "caro", 1.0, new Date()));
        this.addTicket(new Ticket("Ciao", "caro", 1.0, new Date()));
        this.addPass(new Pass("compId", "Castelfidardo", "Urbano 1", "urbano", 15));*/
    }

    boolean isEmpty() {
        if (getMyTickets().size() == 0 && getMyCards().size() == 0 && getMyPasses().size() == 0) return true;
        else return false;
    }

    double getCredit() {
        return this.credit;
    }

    void updateCredit(double recharge) {
        this.credit += recharge;
        databaseSync();
    }

    List<Ticket> getMyTickets() {
        return this.myTickets;
    }

    private void addTicket(Ticket newTicket) {
        myTickets.add(newTicket);
    }

    List<Card> getMyCards() {
        return this.myCards;
    }

    private void addCard(Card newCard) {
        myCards.add(newCard);
    }

    List<Pass> getMyPasses() {
        return this.myPasses;
    }

    private void addPass(Pass newPass) {
        myPasses.add(newPass);
    }

    private void databaseSync() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("clients").child(ProfileActivity.getClient().getUid()).child("myPocket").setValue(this);
    }

}