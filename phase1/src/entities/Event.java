package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class Event implements Serializable, Iterable<UUID> {

    private LocalDateTime eventTime;
    private UUID eventId;
    private String eventTitle;
    private Integer eventRoom;

    private UUID organizerId;
    private UUID speakerId;

    private List<UUID> attendees;

    public Event(String title, LocalDateTime time, UUID id, UUID organizerId, UUID speakerId, List<UUID> attendees,
                 int room) {
        eventTime = time;
        eventTitle = title;
        eventRoom = room;

        eventId = id;
        this.organizerId = organizerId;
        this.speakerId = speakerId;

        this.attendees = attendees;
    }

    public boolean hasAttendee(UUID uuid) {
        for(UUID attendeeId: attendees) {
            if(attendeeId.equals(uuid)){
                return true;
            }
        }
        return false;
    }

    public String getEventTitle() {return eventTitle;}

    public UUID getId() {return eventId;}

    public UUID getOrganizerId() {return organizerId;}

    public UUID getSpeakerId() {return speakerId;}

    public LocalDateTime getEventTime() {return eventTime;}

    public int getEventRoom(){return eventRoom;}

    public void addAttendee(User attendee){
        attendees.add(attendee.getId());
    }

    @Override
    public Iterator iterator() {
        return attendees.iterator();
    }

}
