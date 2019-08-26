package com.ezbus.purchase;

import com.ezbus.authentication.DataSync;
import com.ezbus.authentication.ProfileActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta il portafoglio personale del cliente.
 * Contiene le liste degli oggetti acquistati e il credito residuo.
 */

public class Pocket implements DataSync {

    private double credit;
    private List<Pass> myPass = new ArrayList<>();
    private List<Card> myCards = new ArrayList<>();
    private List<Ticket> myTickets = new ArrayList<>();


    public Pocket() {
        this.credit = 0;
    }

    private boolean isEmpty() {
        return (getMyTickets().size() == 0 && getMyCards().size() == 0 && getMyPass().size() == 0);
    }

    double getCredit() {
        return this.credit;
    }

    void updateCredit(double recharge) {
        this.credit += recharge;
        databaseSync();
    }

    List<Pass> getMyPass() {
        return this.myPass;
    }

    List<Card> getMyCards() {
        return this.myCards;
    }

    List<Ticket> getMyTickets() {
        return this.myTickets;
    }

    boolean add(Document newDocument) {
        if (newDocument instanceof Ticket) return add((Ticket) newDocument);
        else if (newDocument instanceof Card) return add((Card) newDocument);
        else return add((Pass) newDocument);
    }

    private boolean add(Pass newPass) {
        for(Pass pass : myPass)
            if (newPass.getId().equals(pass.getId())) return true;
        myPass.add(newPass);
        updateCredit(-newPass.getPrice());
        return false;
    }

    private boolean add(Card newCard) {
        for(Card card : myCards)
            if (newCard.getRouteId().equals(card.getRouteId())) return true;
        myCards.add(newCard);
        updateCredit(-newCard.getPrice());
        return false;
    }

    private boolean add(Ticket newTicket) {
        search(newTicket);
        updateCredit(-newTicket.getPrice());
        return false;
    }

    private void search(Ticket newTicket) {
        for(Ticket ticket : getMyTickets()) {
            if (newTicket.getStart().equals(ticket.getStart()) && newTicket.getEnd().equals(ticket.getEnd())) {
                ticket.setNumber(ticket.getNumber()+1);
                return;
            }
        }
        getMyTickets().add(newTicket);
    }

    //Controlla quali titoli di viaggio sono scaduti e in tal caso li elimina dal database
    void checkDocuments() {
        if (!isEmpty()) {
            boolean check = false;
            List<Pass> validPass = new ArrayList<>();
            List<Card> validCards = new ArrayList<>();
            List<Ticket> validTickets = new ArrayList<>();

            for(Pass pass : getMyPass()) {
                if (pass.isValid()) validPass.add(pass);
                else check = true;
            }
            for(Card card : getMyCards()) {
                if (card.isValid()) validCards.add(card);
                else check = true;
            }
            for(Ticket ticket : getMyTickets()) {
                if (ticket.isValid()) validTickets.add(ticket);
                else check = true;
            }

            this.myPass = validPass;
            this.myCards = validCards;
            this.myTickets = validTickets;

            if (check) databaseSync();
        }
    }

    public void databaseSync() {
        DatabaseReference path = FirebaseDatabase.getInstance().getReference("/clients");
        path.child(ProfileActivity.getClient().getUid()).child("myPocket").setValue(this);
    }

}