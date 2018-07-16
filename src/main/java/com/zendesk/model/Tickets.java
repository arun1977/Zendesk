package com.zendesk.model;

import java.util.List;

public class Tickets {
    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    private List<Ticket> tickets;

}
