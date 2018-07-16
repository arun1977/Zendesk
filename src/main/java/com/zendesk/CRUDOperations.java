package com.zendesk;

import com.google.gson.Gson;
import com.zendesk.model.Ticket;
import com.zendesk.model.Tickets;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Base64;
//import static org.hamcrest.Matchers.*;

public class CRUDOperations {

    public static void setup(){
        RestAssured.baseURI = Constants.BASE_URI;
        RestAssured.basePath = Constants.BASE_PATH;
    }

    private static Header getAuthHeader() {
        Header header = new Header("Authorization: Basic " +Constants.BASE64_TOKEN, null);
        return header;
        //return "Basic " + Constants.BASE64_TOKEN;
    }

    public static Header getAuthHeaderInvalid(){
        byte[] encodedBytes = Base64.getEncoder().encode(("%2Ftoken:" +Constants.TOKEN).getBytes());
        Header header = new Header(null,"Authorization: Basic " +new String(encodedBytes));
        return header;
    }

    public static Ticket createTicket(Ticket newTicket){
        Ticket createdTicket = null;
        RequestSpecification request = createRequest();
        request.body(createTicketJson(newTicket));
        Response response = request.post(Constants.CREATE_TICKET_ENDPOINT);
        if (response.getStatusCode() == 201) {
            createdTicket = response.as(Ticket.class);
        }
        return createdTicket;
    }

    public static Tickets getTickets() {
        RequestSpecification request = createRequest();
        Response response = request.get(Constants.LIST_ALL_TICKETS_ENDPOINT);
        Tickets tickets = null;

        if (response.getStatusCode() == 200) {
            tickets = response.as(Tickets.class);
        }
        return tickets;
    }

    public static Ticket getTicket(int ticketId) {
        RequestSpecification request = createRequest();
        Response response = request.get(Constants.PUT_DELETE_TICKET_ENDPOINT.replace("{id}", String.valueOf(ticketId)));
        Ticket ticket = null;

        if (response.getStatusCode() == 200) {
            ticket = response.as(Ticket.class);
        }
        return ticket;
    }

    public static void deleteTicket(int ticketId) {
        RequestSpecification request = createRequest();
        Response response = request.delete(Constants.PUT_DELETE_TICKET_ENDPOINT.replace("{id}", String.valueOf(ticketId)));
    }

    public static void addComment(Ticket ticket, String comment){
        RequestSpecification request = createRequest();
        request.body(createTicketJson(ticket));
        Response response = request.put(Constants.PUT_DELETE_TICKET_ENDPOINT.replace("{id}", String.valueOf(ticket.getId())));
    }

    public static String createTicketJson(Ticket newTicket) {
        Gson ticket = new Gson();
        String ticketJson = ticket.toJson(newTicket);
        return ticketJson;
    }

    private static RequestSpecification createRequest() {
        RequestSpecification request = RestAssured.given();
        Header contentType = new Header("Content-Type: "  +ContentType.JSON.toString(), null);
        Header acceptContent = new Header("Accept: " +ContentType.JSON.toString(), null);
        request.header(getAuthHeader()).and().header(contentType).and().header(acceptContent);
        return request;
    }
}