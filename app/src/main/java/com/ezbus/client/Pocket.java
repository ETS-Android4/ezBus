package com.ezbus.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pocket {

    private boolean empty;
    private double credit;
    private List<Ticket> myTickets = new ArrayList<Ticket>();
    private List<Card> myCards = new ArrayList<Card>();
    private List<Pass> myPasses = new ArrayList<Pass>();


    public Pocket() {
        this.empty = true;
        this.addCard(new Card("Ciao", "caro", 1.0, new Date(), 3));
        this.addTicket(new Ticket("Ciao", "caro", 1.0, new Date()));
        this.addTicket(new Ticket("Ciao", "caro", 1.0, new Date()));
        this.addPass(new Pass("compId", "Castelfidardo", "Urbano 1", "urbano", 15));
    }

    private boolean isEmpty() {
        return this.empty;
    }

    double getCredit() {
        return this.credit;
    }

    private void setCredit(double credit) {
        this.credit = credit;
    }

    public void updateCredit(double recharge) {
        setCredit(getCredit() + recharge);
    }

    List<Ticket> getMyTickets() {
        return this.myTickets;
    }

    private void addTicket(Ticket newTicket) {
        myTickets.add(newTicket);
        if (isEmpty()) empty = false;
    }

    List<Card> getMyCards() {
        return this.myCards;
    }

    private void addCard(Card newCard) {
        myCards.add(newCard);
        if (isEmpty()) empty = false;
    }

    List<Pass> getMyPasses() {
        return this.myPasses;
    }

    private void addPass(Pass newPass) {
        myPasses.add(newPass);
        if (isEmpty()) empty = false;
    }

}