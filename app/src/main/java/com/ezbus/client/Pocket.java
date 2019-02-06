package com.ezbus.client;

import java.util.List;

public class Pocket {

    private boolean empty;
    private double credit;
    private List<Ticket> myTickets;
    private List<Card> myCards;
    private List<Pass> myPasses;


    public Pocket() {
        this.empty = true;
    }

    public Pocket(List<Ticket> tickets, List<Card> cards, List<Pass> passes) {
        this.empty = false;
        this.myTickets = tickets;
        this.myCards = cards;
        this.myPasses = passes;
    }

    public boolean isEmpty() {
        return empty;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public void updateCredit(double recharge) {
        setCredit(getCredit() + recharge);
    }

    public List<Ticket> getMyTickets() {
        return myTickets;
    }

    public void addTicket(Ticket newTicket) {
        myTickets.add(newTicket);
        if (isEmpty()) empty = false;
    }

    public List<Card> getMyCards() {
        return myCards;
    }

    public void addCard(Card newCard) {
        myCards.add(newCard);
        if (isEmpty()) empty = false;
    }

    public List<Pass> getMyPasses() {
        return myPasses;
    }

    public void addPass(Pass newPass) {
        myPasses.add(newPass);
        if (isEmpty()) empty = false;
    }

}