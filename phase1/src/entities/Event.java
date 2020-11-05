package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Event implements Serializable {

    private LocalDateTime eventTime;
    private UUID eventId;
    private String eventTitle;

    public Event(String title, LocalDateTime time, UUID id) {
        eventTime = time;
        eventTitle = title;

        eventId = id;
    }

    public String getEventTitle() {return eventTitle;}

    public UUID getId() {return eventId;}

    public LocalDateTime getEventTime() {return eventTime;}
}
