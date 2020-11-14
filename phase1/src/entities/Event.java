package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Represents an event at the tech conference
 */
public class Event implements Serializable, Iterable<UUID> {

    private LocalDateTime eventTime;
    private UUID eventId;
    private String eventTitle;
    private Integer eventRoom;

    private UUID organizerId;
    private UUID speakerId;

    private List<UUID> attendees;

    /**
     * Creates an Event with the given title, time, UUID, organizer UUID, speaker UUID,
     * list of attendees, and room number
     * @param title - the Events title
     * @param time - the time the Event begins
     * @param id - the UUID of the Event
     * @param organizerId - the UUID of the Organizer of the Event
     * @param speakerId - the UUID of the Speaker of the Event
     * @param attendees - a list of attendees UUID's for the Event
     * @param room - the room number the Event will be hosted in
     */
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

    /**
     * Checks if an attendee is attending this Event
     *
     * @param uuid The UUID of the attendee in question
     * @return a boolean value representing whether or not the attendee
     * is attending this Event
     */
    public boolean hasAttendee(UUID uuid) {
        for(UUID attendeeId: attendees) {
            if(attendeeId.equals(uuid)){
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the title of the Event
     * @return a String - the title of the Event
     */
    public String getEventTitle() {return eventTitle;}

    /**
     * Gets the UUID of the Event
     * @return a UUID - the UUID of the Event
     */
    public UUID getId() {return eventId;}

    /**
     * Gets the organizer of this Events UUID
     * @return a UUID - the UUID of this Events organizer
     */
    public UUID getOrganizerId() {return organizerId;}

    /**
     * Gets the speaker of this Events UUID
     * @return a UUId - the UUID of this Events speaker
     */
    public UUID getSpeakerId() {return speakerId;}

    /**
     * Gets the time that this Event begins
     * @return a LocalDateTime - the time that this Event begins
     */
    public LocalDateTime getEventTime() {return eventTime;}

    /**
     * Gets the room number of the Event
     * @return an integer - the room number of this event
     */
    public int getEventRoom(){return eventRoom;}

    /**
     * Adds an attendee to this Events list of Attendees
     * @param attendee - the attendee to add
     */
    public void addAttendee(User attendee){
        attendees.add(attendee.getId());
    }

    /**
     * Removes an attendee to this Events list of Attendees
     * @param attendee - the attendee to remove
     */
    public void removeAttendee(User attendee){
        attendees.remove(attendee.getId());
    }

    /**
     * This method is necessary to make Event iterable
     * @return the iterator of <attendees>
     */
    @Override
    public Iterator<UUID> iterator() {
        return attendees.iterator();
    }
}
