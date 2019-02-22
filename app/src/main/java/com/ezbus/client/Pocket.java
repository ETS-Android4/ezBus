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
        searchTicket(myTickets, newTicket);
        updateCredit(-newTicket.getPrice());
    }

    private void searchTicket(List<Ticket> list, Ticket newTicket) {
        for(Ticket ticket : list) {
            if (newTicket.getStart().equals(ticket.getStart()) && newTicket.getEnd().equals(ticket.getEnd())) {
                ticket.setNumber(ticket.getNumber()+1);
                return;
            }
        }
        list.add(newTicket);
    }

    List<Card> getMyCards() {
        return this.myCards;
    }

    boolean addCard(Card newCard) {
        for(Card card : myCards)
            if (newCard.getRouteId().equals(card.getRouteId())) return true;
        myCards.add(newCard);
        updateCredit(-newCard.getPrice());
        return false;
    }

    List<Pass> getMyPass() {
        return this.myPass;
    }

    boolean addPass(Pass newPass) {
        for(Pass pass : myPass)
            if (newPass.getId().equals(pass.getId())) return true;
        myPass.add(newPass);
        updateCredit(-newPass.getPrice());
        return false;
    }

    void databaseSync() {
        DatabaseReference path = FirebaseDatabase.getInstance().getReference("/clients");
        path.child(ProfileActivity.getClient().getUid()).child("myPocket").setValue(this);
    }
}