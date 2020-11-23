package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * Represents an event at the tech conference
 */
public class Event implements Serializable, Iterable<UUID> {

    private final LocalDateTime eventTime;
    private final UUID eventId;
    private final String eventTitle;
    private final Integer eventRoom;
    private final Integer eventCapacity;
    private final String eventType;

    private final UUID organizerId;
    private final UUID speakerId;

    private final List<UUID> attendees;

    /**
     * Creates an Event with the given title, time, UUID, organizer UUID, speaker UUID,
     * list of attendees, and room number
     *
     * @param title       - the Events title
     * @param time        - the time the Event begins
     * @param id          - the UUID of the Event
     * @param organizerId - the UUID of the Organizer of the Event
     * @param speakerId   - the UUID of the Speaker of the Event
     * @param attendees   - a list of attendees UUID's for the Event
     * @param room        - the room number the Event will be hosted in
     * @param capacity    - the maximum number of Attendee of the Event
     * @param type    - the maximum number of Attendee of the Event
     */
    public Event(String title, LocalDateTime time, UUID id, UUID organizerId, UUID speakerId, List<UUID> attendees,
                 int room, int capacity, String type) {
        eventTime = time;
        eventTitle = title;
        eventRoom = room;
        eventCapacity = capacity;
        eventType = type;

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
        for (UUID attendeeId : attendees) {
            if (attendeeId.equals(uuid)) return true;
        }
        return false;
    }

    /**
     * Checks if this Event is full
     *
     * @return a boolean value, True if number of Attendee reaches maximum capacity.
     */
    public boolean isFull() {
    return attendees.size() == eventCapacity;
    }

    /**
     * Gets the title of the Event
     *
     * @return a String - the title of the Event
     */
    public String getEventTitle() {
        return eventTitle;
    }

    /**
     * Gets the UUID of the Event
     *
     * @return a UUID - the UUID of the Event
     */
    public UUID getId() {
        return eventId;
    }

    /**
     * Gets the organizer of this Events UUID
     *
     * @return a UUID - the UUID of this Events organizer
     */
    public UUID getOrganizerId() {
        return organizerId;
    }

    /**
     * Gets the speaker of this Events UUID
     *
     * @return a UUId - the UUID of this Events speaker
     */
    public UUID getSpeakerId() {
        return speakerId;
    }

    /**
     * Gets the time that this Event begins
     *
     * @return a LocalDateTime - the time that this Event begins
     */
    public LocalDateTime getEventTime() {
        return eventTime;
    }

    /**
     * Gets the room number of the Event
     *
     * @return an integer - the room number of this event
     */
    public int getEventRoom() {
        return eventRoom;
    }

    /**
     * Gets maximum capacity of this event
     *
     * @return an integer - the room number of this event
     */
    public int getEventCapacity() {
        return eventCapacity;
    }

    /**
     * Gets number of attendees that currently enrolled in the event
     *
     * @return an integer - the number of attendees enrolled
     */
    public int getEventEnrolledNumber() {
        return attendees.size();
    }

    /**
     * Gets type of the event
     *
     * @return a string - the type of the event
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Adds an attendee to this Events list of Attendees
     *
     * @param attendee - the attendee to add
     */
    public void addAttendee(User attendee) {
        attendees.add(attendee.getId());
    }

    /**
     * Removes an attendee to this Events list of Attendees
     *
     * @param attendee - the attendee to remove
     */
    public void removeAttendee(User attendee) {
        attendees.remove(attendee.getId());
    }

    /**
     * This method is necessary to make Event iterable
     *
     * @return the iterator of <attendees>
     */
    @Override
    public Iterator<UUID> iterator() {
        return attendees.iterator();
    }
}
