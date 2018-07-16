package com.zendesk;

import com.zendesk.model.Ticket;
import com.zendesk.model.Tickets;

public class ZenDeskTests {
    private void init(){
        CRUDOperations.setup();
    }

    private void createTicket(){
        Ticket newTicket = new Ticket();
        newTicket.setSubject("My printer is on fire!");
        Ticket.Comment newComment = newTicket.new Comment();
        newComment.setBody("The smoke is very colorful.");
        newTicket.setComment(newComment);
        Ticket createdTicket = CRUDOperations.createTicket(newTicket);

        // Validate the created ticket
        Validate.validateTicket(newTicket, createdTicket);
    }

    private void createTicketWithoutComment() {
        Ticket newTicket = new Ticket();
        newTicket.setSubject("My printer is on fire!");
        Ticket.Comment newComment = newTicket.new Comment();
        newTicket.setComment(newComment);
        CRUDOperations.createTicket(newTicket);
    }

    private void addCommentToTicket() {
        Tickets tickets = CRUDOperations.getTickets();
        Ticket ticket = tickets.getTickets().get(0);
        CRUDOperations.addComment(ticket, "The fire has been put out");
    }

    private void getAllTickets() {
        Tickets tickets = CRUDOperations.getTickets();
        for (Ticket ticket : tickets.getTickets()) {
            System.out.println("Ticket id: " +ticket.getId());
            System.out.println("Ticket subject: " +ticket.getSubject());
        }
    }

    private void deleteExistingTicket() {
        Tickets tickets = new Tickets();
        Ticket ticket = tickets.getTickets().get(0);
        CRUDOperations.deleteTicket(ticket.getId());
    }

    public static void main(String[] args){
        ZenDeskTests test  = new ZenDeskTests();
        test.init();
        test.createTicket();
        test.addCommentToTicket();
        test.getAllTickets();
        test.deleteExistingTicket();
    }
}