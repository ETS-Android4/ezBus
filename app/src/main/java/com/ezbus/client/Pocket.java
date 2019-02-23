package com.ezbus.client;

import com.ezbus.authentication.DataSync;
import com.ezbus.authentication.ProfileActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Pocket implements DataSync {

    private double credit;
    private List<Ticket> myTickets = new ArrayList<>();
    private List<Card> myCards = new ArrayList<>();
    private List<Pass> myPass = new ArrayList<>();


    public Pocket() {
        this.credit = 0;
    }

    private boolean isEmpty() {
        if (getMyTickets().size() == 0 && getMyCards().size() == 0 && getMyPass().size() == 0) return true;
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

    List<Card> getMyCards() {
        return this.myCards;
    }

    List<Pass> getMyPass() {
        return this.myPass;
    }

    void add(Ticket newTicket) {
        searchTicket(myTickets, newTicket);
        updateCredit(-newTicket.getPrice());
    }

    boolean add(Card newCard) {
        for(Card card : myCards)
            if (newCard.getRouteId().equals(card.getRouteId())) return true;
        myCards.add(newCard);
        updateCredit(-newCard.getPrice());
        return false;
    }

    boolean add(Pass newPass) {
        for(Pass pass : myPass)
            if (newPass.getId().equals(pass.getId())) return true;
        myPass.add(newPass);
        updateCredit(-newPass.getPrice());
        return false;
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

    void checkDocuments() {
        if (!isEmpty()) {
            boolean check = false;
            List<Ticket> validTickets = new ArrayList<>();
            List<Card> validCards = new ArrayList<>();
            List<Pass> validPass = new ArrayList<>();

            for(Card card : getMyCards()) {
                if (card.isValid()) validCards.add(card);
                else check = true;
            }
            for(Ticket ticket : getMyTickets()) {
                if (ticket.isValid()) validTickets.add(ticket);
                else check = true;
            }
            for(Pass pass : getMyPass()) {
                if (pass.isValid()) validPass.add(pass);
                else check = true;
            }

            this.myTickets = validTickets;
            this.myCards = validCards;
            this.myPass = validPass;

            if (check) databaseSync();
        }
    }

    public void databaseSync() {
        DatabaseReference path = FirebaseDatabase.getInstance().getReference("/clients");
        path.child(ProfileActivity.getClient().getUid()).child("myPocket").setValue(this);
    }

}