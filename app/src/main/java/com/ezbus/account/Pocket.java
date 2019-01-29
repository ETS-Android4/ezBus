package com.ezbus.account;

import java.util.List;

class Pocket {

    private boolean empty;
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

}