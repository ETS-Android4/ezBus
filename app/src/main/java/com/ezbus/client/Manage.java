package com.ezbus.client;

import android.widget.Toast;

import java.util.List;

interface Manage {

    default boolean search(List<Ticket> list, Ticket newTicket) {
        for(Ticket ticket : list) {
            if (newTicket.getStart().equals(ticket.getStart()) && newTicket.getEnd().equals(ticket.getEnd())) {
                ticket.setNumber(ticket.getNumber()+1);
                return true;
            }
        }

        list.add(newTicket);
        return false;
    }

    default boolean search(List<Card> list, Card newCard) {
        for(Card card : list) {
            if (newCard.getRouteId().equals(card.getRouteId())) {
                return true;
            }
        }

        list.add(newCard);
        return false;
    }

    default boolean search(List<Pass> list, Pass newPass) {
        for(Pass pass : list) {
            if (newPass.getId().equals(pass.getId())) {
                return true;
            }
        }

        list.add(newPass);
        return false;
    }

}