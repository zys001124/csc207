package useCaseClasses;

import com.google.firebase.database.DataSnapshot;
import entities.Event;
import entities.User;
import exceptions.*;
import gateways.DataSnapshotReader;
import observers.Observable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    /**
     * Adds an event from a given firebase data snapshot for live updates
     * @param dataSnapshot the snapshot to be processed for the event to be added
     */
    public void addEventFromDataSnapshot(DataSnapshot dataSnapshot) {

        Event event = getFromDataSnapshot(dataSnapshot);
        if (!eventExists(event.getId())) {
            List<Event> eventsToAdd = new ArrayList<>();
            eventsToAdd.add(event);
            events.addAll(eventsToAdd);
            notifyObservers(eventsToAdd, true, true);
        }
    }

    /**
     * Checks to see if an event exists based on its UUID
     * @param eventId the UUID of the event that is to be found in the conference system
     * @return boolean on if the event is found
     */
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
    public Boolean availabilityInTime(LocalDateTime sTime, LocalDateTime eTime, int roomNumber) {

        for (Event temp : events) {
            LocalDateTime timeStart = temp.getEventTime();
            LocalDateTime timeEnd = temp.getEventETime();
            Integer roomNum = temp.getEventRoom();
            if ((timeStart.isBefore(sTime) && timeEnd.isAfter(sTime)) ||
                    (timeStart.isBefore(eTime) && timeEnd.isAfter(eTime)) ||
                    (timeStart.isAfter(sTime) && timeEnd.isBefore((eTime))) ||
                    (timeStart.isEqual(sTime))
            ) {
                if (roomNum.equals(roomNumber)) {
                    return true;
                }
            }
        }

        return false;
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

    /**
     * Removes an event from the conference system based on the firebase snapshot passed in
     * @param dataSnapshot the snapshot of the event to be removed
     */
    public void removeEventFromDataSnapshot(DataSnapshot dataSnapshot) {
        Event eventChanged = getFromDataSnapshot(dataSnapshot);
        List<Event> eventsToRemove = new ArrayList<>();
        for (Event event : events) {
            int index = events.indexOf(event);
            if (event.getId().equals(eventChanged.getId())) {
                eventsToRemove.add(events.remove(index));
                notifyObservers(eventsToRemove, false, true);
                return;
            }
        }
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

    public List<Event> getEventsWithAttendee(User user) {
        List<Event> result = new ArrayList<>();
        for (Event event : events) {
            if (event.hasAttendee(user.getId())) {
                result.add(event);
            }
        }
        return result;
    }

    /**
     * gets a list of events that a speaker is hosting
     * @param speakerId the UUID of the speakers
     * @return A list of events that the given speaker is hosting
     */
    public List<Event> getEventsWithSpeaker(UUID speakerId) {
        List<Event> speakerEvents = new ArrayList<>();
        for (Event e : events) {
            if (e.getSpeakerId().contains(speakerId)) {
                speakerEvents.add(e);
            }
        }
        return speakerEvents;
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

    /**
     * Adds a user to an event
     * @param attendee the User to be added to the event
     * @param event the event for the user to join
     */
    private void addingUser(User attendee, Event event) {
        List<Event> eventsChanged = new ArrayList<>();
        event.addAttendee(attendee);
        eventsChanged.add(event);
        notifyObservers(eventsChanged, true, false);
    }

    /**
     * updates the information of an event based on the firebase updates for the data snapshot
     * @param dataSnapshot the snapshot that stores info on what changes about the event
     */
    public void updateEventFromDataSnapshot(DataSnapshot dataSnapshot) {
        Event event = getFromDataSnapshot(dataSnapshot);
        Event.EventData eventData = event.getEventData();
        // Find event
        List<Event> eventsToChange = new ArrayList<>();

        for (Event e : events) {
            if (e.getId().equals(event.getId())) {
                e.set(eventData);
                eventsToChange.add(e);
                System.out.println("here");
                notifyObservers(eventsToChange, true, true);
                return;
            }
        }
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

    /**
     * Removes a user from an event
     * @param attendee the attendee that is to be removed from an event
     * @param event the event the user is going to leave
     */
    private void removingUser(User attendee, Event event) {
        List<Event> eventsChanged = new ArrayList<>();
        event.removeAttendee(attendee);
        eventsChanged.add(event);
        notifyObservers(eventsChanged, true, false);
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

    /**
     * Helper method that sorts the events based on their time
     * @param result the sorted arraylist of events based on their time
     */
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

    /**
     * Gets an arraylist of events where the events are sorted with attendees
     * @param user the user for the sorted events
     * @return ArrayList of events with attendees
     */
    public ArrayList<Event> sortedEventsWithAttendees(User user) {
        return eventSortTime(getEventsWithAttendee(user));
    }

    /**
     * Gets the title of an event
     * @param event the event to get the title of
     * @return String of the title of this event
     */
    public String getIndividualTitle(Event event) {
        return event.getEventTitle();
    }

    /**
     * gets the time of the event based on the given format
     * @param event the event we want the time of
     * @param format the format of the time we want in this case
     * @return the String version of the event
     */
    public String getIndividualTime(Event event, String format) {
        return event.getEventTime().format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * Gets the room of the event
     * @param event the event of the room we want
     * @return String of the room number
     */
    public String getIndividualRoom(Event event) {
        return String.valueOf(event.getEventRoom());
    }

    /**
     * The capacity of the event given
     * @param event the event we want to get the capacity of
     * @return String value of the capacity
     */
    public String getIndividualCapacity(Event event) {
        return String.valueOf(event.getEventCapacity());
    }

    /**
     * gets The number of users enrolled in an event
     * @param event the event we want to get the number of users enrolled in
     * @return String value of the enrollment number
     */
    public String getIndividualEnrolledNumber(Event event) {
        return String.valueOf(event.getEventEnrolledNumber());
    }

    /**
     * The type of event that is being passed in
     * @param event the event we want the type of
     * @return String value of the type of event
     */
    public String getIndividualType(Event event) {
        return String.valueOf(event.getEventType().toString());
    }

    /**
     * Returns on if the event is VIP only
     * @param event the event we want to check if it is VIP only
     * @return String value of the VIP only message
     */
    public String getIndividualVIP(Event event) {
        return String.valueOf(event.getViponly());
    }

    /**
     * Changes the event capacity of the event passed in
     * @param eventTitle the title of the event as a string
     * @param newCapacity the capacity of the event we want to change to
     * @param changeFromDatabase boolean on changes from database
     */
    public void changeEventCapacity(String eventTitle, int newCapacity, boolean changeFromDatabase) {
        List<Event> eventsToChange = new ArrayList<>();
        Event event = getEvent(eventTitle);
        event.setEventCapacity(newCapacity);
        eventsToChange.add(event);
        notifyObservers(eventsToChange, true, changeFromDatabase);
    }

    /**
     * gets a list of events the user passed in is hosting
     * @param u the user that is hosting the returned events
     * @return A list of strings of the event user u is hosting
     */
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

    /**
     * This method checks whether the event exist by its name
     *
     * @param title A string that is the name of the event
     * @return A boolean value of whether this event exist
     */
    public boolean eventTitleExists(String title) {
        for (Event e : events) {
            if (e.getEventTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This helper method returns the event id of the given event
     *
     * @param event An event
     * @return the id of the event
     */
    public UUID getEventId(Event event) {
        return event.getId();
    }

    /**
     * A helper method that generates the label texts for the change event capacity scene.
     *
     * @return A list of stings that will become label texts in the change event capacity scene
     */
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

    /**
     * A helper method that generates the label texts for the change event cancel scene.
     *
     * @return A list of stings that will become label texts in the change event cancel scene
     */
    public List<String> getEventCancelLabels() {
        ArrayList<String> labels = new ArrayList<>();
        for (Event event : events) {
            String labelText = event.getEventTitle() + " on " + event.getEventTime().format(DateTimeFormatter.ofPattern("MMMM dd, HH:mm")) + ".";
            if (event.getSpeakerId().size() > 0) {
                labelText = labelText + " Hosted by";
            }
            labels.add(labelText);
        }
        return labels;
    }

    /**
     * A helper method that generates the list of list of ids of speakers of each event.
     *
     * @return A list of list of ids of speakers of each event.
     */
    public List<List<UUID>> getSpeakersId() {
        ArrayList<List<UUID>> ids = new ArrayList<>();
        for (Event event : events) {
            ids.add(event.getSpeakerId());
        }
        return ids;
    }

    /**
     * Gets the event entity from the given data snapshot from firebase
     * @param dataSnapshot the snapshot from firebase that is updated automatically
     * @return Event entity of this conference system
     */
    @Override
    public Event getFromDataSnapshot(DataSnapshot dataSnapshot) {
        Event.EventData data = eventDataFromDataSnapshot(dataSnapshot);
        return Event.fromEventData(data);
    }

    /**
     * All of the data of the event from a given firebase data snapshot that is to be updated
     * automatically
     * @param dataSnapshot the snapshot of the data to be passed
     * @return EventData of the entire EventManager
     */
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
