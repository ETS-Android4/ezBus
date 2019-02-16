package com.ezbus.client;

import com.ezbus.authentication.ProfileActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Pocket {

    private double credit;
    private List<Ticket> myTickets = new ArrayList<>();
    private List<Card> myCards = new ArrayList<>();
    private List<Pass> myPasses = new ArrayList<>();


    public Pocket() {
        this.credit = 0;
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

    void addTicket(Ticket newTicket) {
        myTickets.add(newTicket);
        databaseSync();
    }

    List<Card> getMyCards() {
        return this.myCards;
    }

    void addCard(Card newCard) {
        myCards.add(newCard);
        databaseSync();
    }

    List<Pass> getMyPasses() {
        return this.myPasses;
    }

    void addPass(Pass newPass) {
        myPasses.add(newPass);
        databaseSync();
    }

    private void databaseSync() {
        DatabaseReference path = FirebaseDatabase.getInstance().getReference("/clients");
        path.child(ProfileActivity.getClient().getUid()).child("myPocket").setValue(this);
    }

}