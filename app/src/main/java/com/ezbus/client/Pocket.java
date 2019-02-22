package com.ezbus.client;

import com.ezbus.authentication.ProfileActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Pocket implements Manage {

    private double credit;
    private List<Ticket> myTickets = new ArrayList<>();
    private List<Card> myCards = new ArrayList<>();
    private List<Pass> myPass = new ArrayList<>();


    public Pocket() {
        this.credit = 0;
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
        search(myTickets, newTicket);
        updateCredit(-newTicket.getPrice());
    }

    List<Card> getMyCards() {
        return this.myCards;
    }

    boolean addCard(Card newCard) {
        if (search(myCards, newCard)) return true;
        else {
            updateCredit(-newCard.getPrice());
            return false;
        }
    }

    List<Pass> getMyPass() {
        return this.myPass;
    }

    boolean addPass(Pass newPass) {
        if (search(myPass, newPass)) return true;
        else {
            updateCredit(-newPass.getPrice());
            return false;
        }
    }

    private void databaseSync() {
        DatabaseReference path = FirebaseDatabase.getInstance().getReference("/clients");
        path.child(ProfileActivity.getClient().getUid()).child("myPocket").setValue(this);
    }

}