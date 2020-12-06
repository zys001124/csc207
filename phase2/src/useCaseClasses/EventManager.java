package useCaseClasses;

import com.google.firebase.database.DataSnapshot;
import entities.Event;
import entities.User;
import exceptions.*;
import gateways.DataSnapshotReader;
import javafx.scene.control.Label;
import observers.Observable;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Represents an eventManager that can modify events of the conference
 */
public class EventManager extends Observable implements DataSnapshotReader<Event> {

    private final List<Event> events;

    /***
     * Create an EventManager with given list of events
     * @param events list of events
     */
    public EventManager(List<Event> events) {
        this.events = events;
    }

    public EventManager() {
        this.events = new ArrayList<>();
    }

    /***
     *add an given event if the conditions check out
     * @param e the event that is gonna be added
     */
    public void addEvent(Event e) {
        List<Event> eventsToAdd = new ArrayList<>();
        eventsToAdd.add(e);
        events.addAll(eventsToAdd);
        notifyObservers(eventsToAdd, true, false);
    }

    public void addEvent(String title, LocalDateTime startTime, LocalDateTime endTime, UUID id, UUID organizerId, List<UUID> speakerId,
                         List<UUID> attendees, int room, int capacity, boolean VIPonly) {

        Event e = new Event(title, startTime, endTime, id, organizerId, speakerId,
                attendees, room, capacity, VIPonly);

        addEvent(e);
    }

    public void addEventFromDataSnapshot(DataSnapshot dataSnapshot) {

        Event event = getFromDataSnapshot(dataSnapshot);
        if (!eventExists(event.getId())) {
            List<Event> eventsToAdd = new ArrayList<>();
            eventsToAdd.add(event);
            events.addAll(eventsToAdd);
            notifyObservers(eventsToAdd, true, true);
        }
    }

    private boolean eventExists(UUID eventId) {
        for (Event event : events) {
            if (eventId.equals(event.getId())) {
                return true;
            }
        }
        return false;
    }

    /***
     * checks the availability of rooms in the given time
     * @param sTime the start time of the event to be checked
     * @param eTime the end time of the event to be checked
     * @return ArrayList<Integer> rooms occupied at some point on the interval [sTime, eTime]
     */
    public ArrayList<Integer> availabilityInTime(LocalDateTime sTime, LocalDateTime eTime) {
        ArrayList<Integer> roomTaken = new ArrayList<>();

        for (Event temp : events) {
            LocalDateTime timeStart = temp.getEventTime();
            LocalDateTime timeEnd = temp.getEventETime();
            Integer roomNum = temp.getEventRoom();
            if (timeStart.isBefore(sTime) && timeEnd.isAfter(sTime) ||
                    (timeStart.isBefore(eTime) && timeEnd.isAfter(eTime)) ||
                    (timeStart.isAfter(sTime) && timeEnd.isBefore((eTime)))
            ) {
                if (!roomTaken.contains(roomNum)) {
                    roomTaken.add(roomNum);
                }
            }
            if (roomTaken.size() == 6) {
                break;
            }
        }

        return roomTaken;
    }

    /**
     * removes an event in the given event list where the event is given by its UUID
     * returns null if event can't be found.
     *
     * @param id The UUID of the event to be found
     * @return the event that is being removed or null if it can't be found.
     */
    public Event removeEvent(UUID id) {
        List<Event> eventsToRemove = new ArrayList<>();
        for (Event event : events) {
            int index = events.indexOf(event);
            if (event.getId().equals(id)) {
                eventsToRemove.add(events.remove(index));
                notifyObservers(eventsToRemove, false, false);
                return eventsToRemove.get(0);
            }
        }
        return null;
    }

    public void removeEventFromDataSnapshot(DataSnapshot dataSnapshot) {
        Event event = getFromDataSnapshot(dataSnapshot);
        removeEvent(event.getId());
//        List<Event> eventsToRemove = new ArrayList<>();
//        for (Event event : events) {
//            int index = events.indexOf(event);
//            if (event.getId().equals(id)) {
//                eventsToRemove.add(events.remove(index));
//                notifyObservers(eventsToRemove, false, false);
//                return;
//            }
//        }
    }

    /**
     * find and return the event in the given event list whose own UUid matches the given UUID
     * returns null if no such event is found
     *
     * @param id the UUID of the event wanted to be found
     * @return the event wanted to be found or null if it does not exist
     */
    public Event getEvent(UUID id) {
        for (Event event : events) {
            if (event.getId().equals(id)) {
                return event;
            }
        }
        return null;
    }

    /**
     * gets the event in the event list and returns that event. Returns null if event can't be found
     *
     * @param event the string name of the event to be found in the list
     * @return the event that corresponds to the given string name. reuturns null if it can't be fond.
     */
    public Event getEvent(String event) {
        for (Event e : events) {
            if (e.getEventTitle().equals(event)) {
                return e;
            }
        }
        return null;
    }

    /**
     * get and return the list of all events that is in the event list
     *
     * @return the list of all events that is in the event list
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Check whether the given user u is the organizer of the given event e
     * return true if u is the organizer of e and false if u is not
     *
     * @param u the given user
     * @param e the given event
     * @return true if u is the organizer of e and false if u is not
     */
    public boolean hasOrganizedEvent(User u, Event e) {
        return e.getOrganizerId().equals(u.getId());
    }

    /**
     * Check whether the given user u is the speaker of the given event e
     * return true if u is the speaker of e and false if u is not
     *
     * @param u the given user
     * @param e the given event
     * @return true if u is the speaker of e and false if u is not
     */
    public boolean isSpeakerForEvent(User u, Event e) {
        return e.getSpeakerId().contains(u.getId());
    }

    /**
     * Find the event in the list based on user input index and add this user to the event
     *
     * @param eventInput The name of the event in the event list
     * @param attendee   The user(attendee) to be added to the event
     * @throws EventNotFoundException       if the entered index does not corresponds to a event
     * @throws NumberFormatException        if the input is not a number
     * @throws UserAlreadyEnrolledException if the user(attendee) was already enrolled in the event
     */
    public void addUserToEvent(String eventInput, User attendee) throws EventNotFoundException,
            NumberFormatException, UserAlreadyEnrolledException, EventFullException, InvalidUserTypeException {
        for (Event event : events) {
            if (event.getEventTitle().equals(eventInput)) {
                if (event.getViponly() && !attendee.getType().equals(User.UserType.VIP)) {
                    throw new InvalidUserTypeException(attendee.getType());
                }
                if (event.isFull()) {
                    throw new EventFullException(eventInput);
                }
                if (event.hasAttendee(attendee.getId())) {
                    throw new UserAlreadyEnrolledException();
                }
                addingUser(attendee, event);
                return;
            }
        }
        throw new EventNotFoundException(eventInput);
    }

    private void addingUser(User attendee, Event event) {
        List<Event> eventsChanged = new ArrayList<>();
        event.addAttendee(attendee);
        eventsChanged.add(event);
        notifyObservers(eventsChanged, true, false);
    }

    public void addUserToEventFromDataBase(String eventInput, User attendee) throws EventNotFoundException,
            NumberFormatException, UserAlreadyEnrolledException, EventFullException, InvalidUserTypeException {
        for (Event event : events) {
            if (event.getEventTitle().equals(eventInput)) {
                if (event.getViponly() && !attendee.getType().equals(User.UserType.VIP)) {
                    throw new InvalidUserTypeException(attendee.getType());
                }
                if (event.isFull()) {
                    throw new EventFullException(eventInput);
                }
                if (event.hasAttendee(attendee.getId())) {
                    throw new UserAlreadyEnrolledException();
                }
                addingUser(attendee, event);
                return;
            }
        }
        throw new EventNotFoundException(eventInput);
    }

    public void updateEventFromDataSnapshot(DataSnapshot dataSnapshot) {
        Event event = getFromDataSnapshot(dataSnapshot);

        // Find event
        List<Event> eventsToChange = new ArrayList<>();
        eventsToChange.add(event);
        notifyObservers(eventsToChange, true, true);
    }

    /**
     * Find the event in the list based on user input index and remove this user from the event
     *
     * @param eventInput The name of the event in the event list
     * @param attendee   The user(attendee) to be removed from the event
     * @throws EventNotFoundException          if the entered index does not corresponds to a event
     * @throws NumberFormatException           if the input is not a number
     * @throws UserNotEnrolledInEventException if the user(attendee) was never enrolled in the event
     *                                         in the first place
     */
    public void removeUserFromEvent(String eventInput, User attendee) throws EventNotFoundException,
            NumberFormatException, UserNotEnrolledInEventException {
        for (Event event : events) {
            if (event.getEventTitle().equals(eventInput)) {
                if (!event.hasAttendee(attendee.getId())) {
                    throw new UserNotEnrolledInEventException();
                }
                removingUser(attendee, event);
                return;
            }
        }
        throw new EventNotFoundException(eventInput);
    }

    private void removingUser(User attendee, Event event) {
        List<Event> eventsChanged = new ArrayList<>();
        event.removeAttendee(attendee);
        eventsChanged.add(event);
        notifyObservers(eventsChanged, true, false);
    }

    public void removeUserFromEventFromDatabase(String eventInput, User attendee) throws EventNotFoundException,
            NumberFormatException, UserNotEnrolledInEventException {
        for (Event event : events) {
            if (event.getEventTitle().equals(eventInput)) {
                if (!event.hasAttendee(attendee.getId())) {
                    throw new UserNotEnrolledInEventException();
                }
                removingUser(attendee, event);
                return;
            }
        }
        throw new EventNotFoundException(eventInput);
    }

    public ArrayList<Event> getEventsWithAttendee(User user){
        ArrayList<Event> result = new ArrayList<>();
        for(Event event: events){
            if(event.hasAttendee(user.getId())){
                result.add(event);
            }
        }
        return result;
    }

    /**
     * This method sorts the event list in the order of date and time
     *
     * @return An arraylist with events sorts in order of datetime
     */
    public ArrayList<Event> eventSortTime(List<Event> eventList) {
        ArrayList<Event> result = new ArrayList<>(eventList);
        insertionSortEvents(result);
        return result;
    }

    private void insertionSortEvents(ArrayList<Event> result) {
        for (int i = 1; i < result.size(); i++) {
            Event cur = result.get(i);
            int j = i - 1;
            while (j >= 0 && result.get(j).getEventTime().isAfter(cur.getEventTime())) {
                result.set(j + 1, result.get(j));
                j = j - 1;
            }
            result.set(j + 1, cur);
        }
    }

    public List<Event> getEventsWithSpeaker(UUID speakerId) {
        List<Event> speakerEvents = new ArrayList<>();
        for(Event e: events) {
            if(e.getSpeakerId().contains(speakerId)) {
                speakerEvents.add(e);
            }
        }
        return speakerEvents;
    }

    public void changeEventCapacity(String eventTitle, int newCapacity, boolean changeFromDatabase) {
        List<Event> eventsToChange = new ArrayList<>();
        Event event = getEvent(eventTitle);
        event.setEventCapacity(newCapacity);
        eventsToChange.add(event);
        notifyObservers(eventsToChange, true, changeFromDatabase);
    }

    public List<String> listOfEventsHosting(User u) {
        //returns a list of the events a presenter is hosting
        List<String> theList = new ArrayList<>();
        for (Event e : events) {
            if (e.getSpeakerId().contains(u.getId())) {
                theList.add(e.getEventTitle());
            }
        }
        return theList;
    }

    public boolean eventTitleExists(String title) {
        for (Event e : events) {
            if (e.getEventTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    public List<String> getEventCapacityLabels() {
        ArrayList<String> labels = new ArrayList<>();
        for (Event event : events) {
            int room = event.getEventRoom();
            int capacity = event.getEventCapacity();
            labels.add("Event: " + event.getEventTitle() + "             Room: "
                    + room + "             Current Capacity: " + capacity);
        }
        return labels;
    }

    @Override
    public Event getFromDataSnapshot(DataSnapshot dataSnapshot) {
        Event.EventData data = eventDataFromDataSnapshot(dataSnapshot);
        return Event.fromEventData(data);
    }

    private Event.EventData eventDataFromDataSnapshot(DataSnapshot dataSnapshot) {

        Map eventMap = (Map<String, Object>) dataSnapshot.getValue();
        Event.EventData eventData = new Event.EventData();
        if (eventMap.containsKey("attendees")) {
            eventData.attendees = (Collection<String>) eventMap.get("attendees");
        } else {
            eventData.attendees = new ArrayList<>();
        }

        if (eventMap.containsKey("speakerIds")) {
            eventData.speakerIds = (Collection<String>) eventMap.get("speakerIds");
        } else {
            eventData.speakerIds = new ArrayList<>();
        }
        eventData.eventSTime = (String) eventMap.get("eventSTime");
        eventData.eventETime = (String) eventMap.get("eventETime");
        eventData.organizerId = (String) eventMap.get("organizerId");
        eventData.VIPonly = (String) eventMap.get("VIPonly");
        eventData.eventCapacity = (String) eventMap.get("eventCapacity");
        eventData.eventRoom = (String) eventMap.get("eventRoom");
        eventData.eventId = (String) eventMap.get("eventId");
        eventData.eventTitle = (String) eventMap.get("eventTitle");

        return eventData;

    }
}
