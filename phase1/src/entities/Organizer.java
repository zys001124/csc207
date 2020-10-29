package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Organizer extends Attendee {

    List<Integer> eventsOrganized;

    // Do not need to generate ID as this is handled by Attendee constructor
    public Organizer(String username, String password) {
        super(username, password);
        eventsOrganized = new ArrayList<>();
    }

    // Message a list of recipients
    public List<Message> messageAll(String text, UUID[] recipients) {

        ArrayList<Message> messages = new ArrayList<>();

        for (UUID recipient : recipients) {
            messages.add(new Message(text, getId(), recipient));
        }

        return messages;
    }
}
