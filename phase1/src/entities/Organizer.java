package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Organizer extends Attendee {

    private List<Event> eventsOrganized;

    // Do not need to generate ID as this is handled by Attendee constructor
    public Organizer(String username, String password, UUID id) {
        super(username, password, id);
        eventsOrganized = new ArrayList<>();
    }

    // Message a list of recipients
    // TODO move this to MessageManager
    public List<Message> messageAll(String text, UUID[] recipients) {

        ArrayList<Message> messages = new ArrayList<>();

        for (UUID recipient : recipients) {
            messages.add(new Message(text, getId(), recipient, UUID.randomUUID()));
        }

        return messages;
    }

    public List<Event> getEventsOrganized(){
        return eventsOrganized;
    }
}
