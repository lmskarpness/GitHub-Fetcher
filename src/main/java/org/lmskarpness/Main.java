package org.lmskarpness;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Not enough or too many arguments.");
            System.exit(0);
        }

        String response = Caller.getUser(args[0]);
        if (response == null) {
            System.out.println("Could not find user " + args[0] + " exiting");
            System.exit(0);
        }
        EventParser parser = new EventParser(response);

        List<Event> events = parser.getEvents();
        for (Event event : events) {
            System.out.println(parser.getEventDetails(event));
        }
    }
}