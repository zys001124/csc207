package entities;

import java.util.UUID;

public class Event {

    private int eventTime;
    private UUID eventId;
    private String eventTitle;

    public Event(String title, int time) {
        eventTime = time;
        eventTitle = title;

        eventId = UUID.randomUUID();
    }
}
