package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Represents an event at the tech conference
 */
public class Event implements Serializable, Iterable<UUID>, Comparable<Event> {

    private LocalDateTime eventSTime;
    private LocalDateTime eventETime;
    private  UUID eventId;
    private String eventTitle;
    private Integer eventRoom;
    private Integer eventCapacity;
    private Type eventType;
    private boolean VIPonly;

    private UUID organizerId;
    private List<UUID> speakerId;

    private List<UUID> attendees;

    /**
     * Creates an Event with the given title, time, UUID, organizer UUID, speaker UUID,
     * list of attendees, and room number
     *
     * @param title       - the Events title
     * @param startTime   - the time the Event begins
     * @param endTime     - the time the Event ends
     * @param id          - the UUID of the Event
     * @param organizerId - the UUID of the Organizer of the Event
     * @param speakerId   - the UUID of the Speaker of the Event
     * @param attendees   - a list of attendees UUID's for the Event
     * @param room        - the room number the Event will be hosted in
     * @param capacity    - the maximum number of Attendee of the Event
     * @param VIPonly     - whether this event is for VIPs only
     */
    public Event(String title, LocalDateTime startTime, LocalDateTime endTime,UUID id, UUID organizerId, List<UUID> speakerId,
                 List<UUID> attendees, int room, int capacity, boolean VIPonly) {
        eventSTime = startTime;
        eventETime = endTime;
        eventTitle = title;
        eventRoom = room;
        eventCapacity = capacity;
        setEventType(speakerId);

        eventId = id;
        this.organizerId = organizerId;
        this.speakerId = speakerId;
        this.attendees = attendees;
        this.VIPonly = VIPonly;
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

    private void setEventType(List<UUID> speakerId){
        int n = speakerId.size();
        if (n ==0){
            eventType = Type.PARTY;
        }else if(n==1){
            eventType = Type.TALK;
        }else{eventType = Type.PANEL_Dis;}
    }

    /**
     *
     * @return the eventType based on size of list of speakers.
     */
    public Type getEventType(){
        return eventType;
    }

    /**
     *
     * @return check if the event is for VIPs only
     */
    public boolean getViponly(){
        return VIPonly;
    }

    /**
     * Checks if this Event is full
     *
     * @return a boolean value, True if number of Attendee reaches maximum capacity.
     */
    public boolean isFull() {
    return attendees.size() >= eventCapacity;
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

    public void setEventCapacity(Integer eventCapacity){this.eventCapacity = eventCapacity;}
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
    public List<UUID> getSpeakerId() {
        return speakerId;
    }

    /**
     * Gets the time that this Event begins
     *
     * @return a LocalDateTime - the time that this Event begins
     */
    public LocalDateTime getEventTime() {
        return eventSTime;
    }

    public LocalDateTime getEventETime(){
        return eventETime;
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

    @Override
    public int compareTo(Event o) {
        return eventId.compareTo(o.eventId);
    }

    /**
     * the different types of event
     */
    public enum Type{
        PARTY,
        TALK,
        PANEL_Dis,
    }

    public EventData getEventData() {
        EventData data = new EventData();

        data.eventSTime = eventSTime.toString();
        data.eventETime = eventETime.toString();
        data.eventId = eventId.toString();
        data.eventTitle = eventTitle;
        data.eventRoom = eventRoom.toString();
        data.eventCapacity = eventCapacity.toString();
        data.VIPonly = Boolean.toString(VIPonly);
        data.organizerId = organizerId.toString();

        List<String> speakerIds = new ArrayList<>();

        for(UUID id: speakerId) {
            speakerIds.add(id.toString());
        }
        data.speakerIds = speakerIds;

        List<String> attendeeIds = new ArrayList<>();

        for(UUID id: attendees) {
            attendeeIds.add(id.toString());
        }
        data.attendees = attendeeIds;


        return data;
    }

    public void set(EventData data) {
        eventId = UUID.fromString(data.eventId);
        eventTitle = data.eventTitle;
        eventCapacity = Integer.parseInt(data.eventCapacity);
        eventRoom = Integer.parseInt(data.eventRoom);
        eventSTime = LocalDateTime.parse(data.eventSTime);
        eventETime = LocalDateTime.parse(data.eventETime);

        List<UUID> speakerIds = new ArrayList<>();
        for(String id: data.speakerIds) {
            speakerIds.add(UUID.fromString(id));
        }
        speakerId = speakerIds;
        List<UUID> attendeeIds = new ArrayList<>();
        for(String id: data.attendees) {
            attendeeIds.add(UUID.fromString(id));
        }
        attendees = attendeeIds;

        setEventType(speakerId);
        organizerId = UUID.fromString(data.organizerId);
        VIPonly = Boolean.parseBoolean(data.VIPonly);
    }

    public static Event fromEventData(EventData data) {

        List<UUID> speakerIds = new ArrayList<>();

        for(String id: data.speakerIds) {
            speakerIds.add(UUID.fromString(id));
        }

        List<UUID> attendeeIds = new ArrayList<>();

        for(String id: data.attendees) {
            attendeeIds.add(UUID.fromString(id));
        }

        return new Event(data.eventTitle,
                LocalDateTime.parse(data.eventSTime),
                LocalDateTime.parse(data.eventETime),
                UUID.fromString(data.eventId),
                UUID.fromString(data.organizerId),
                speakerIds,
                attendeeIds,
                Integer.parseInt(data.eventRoom),
                Integer.parseInt(data.eventCapacity),
                Boolean.parseBoolean(data.VIPonly));
    }

    public static class EventData {
        public String eventSTime;
        public String eventETime;
        public String eventId;
        public String eventTitle;
        public String eventRoom;
        public String eventCapacity;
        public String VIPonly;

        public String organizerId;

        public Collection<String> speakerIds;

        public Collection<String> attendees;
    }
}
