package com.zendesk;

import com.zendesk.model.Ticket;
import org.junit.Assert;

public class Validate {
    public static boolean validateTicket (Ticket expected, Ticket actual) {
        try {
            Assert.assertTrue(expected.getSubject().equalsIgnoreCase(actual.getSubject()));
            System.out.println("Ticket subject is: " +actual.getSubject()+ " as expected");
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }
}
