package useCaseClasses;

import entities.Event;
import entities.User;
import exceptions.*;
import observers.Observable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents an eventManager that can modify events of the conference
 */
public class EventManager extends Observable {

    private final List<Event> events;

    /***
     * Create an EventManager with given list of events
     * @param events list of events
     */
    public EventManager(List<Event> events) {
        this.events = events;
    }

    /**
     * Creates a new EventManager object.
     * Initializes <events> field to an empty ArrayList
     */
    public EventManager() {
        this.events = new ArrayList<>();
    }

    /***
     *add an given event if the conditions check out
     * @param e the event that is gonna be added
     */
    public void addEvent(Event e) throws Exception {
        Exception eventAddException = checkValidAddition(e);
        if (eventAddException != null) {
            throw eventAddException;
        }
        List<Event> eventsToAdd = new ArrayList<>();
        eventsToAdd.add(e);
        events.addAll(eventsToAdd);
        notifyObservers(eventsToAdd, true);
    }

    private Exception checkValidAddition(Event e) {

        for (Event event : events) {
            if (event.getEventTitle().equals(e.getEventTitle())) {
                return new EventNameTakenException();
            }
        }

        if (e.getEventTime().isAfter(e.getEventETime())) {
            return new InvalidEventTimeRangeException();
        }

        if (isOccupiedDuringTime(e.getEventTime(), e.getEventETime(), e.getEventRoom())) {
            return new EventBookingOverlapException();
        }

        if (checkSpeakersOccupied(e.getSpeakerId(), e.getEventTime(), e.getEventETime())) {
            return new SpeakerOccupiedException();
        }

        if (e.getEventCapacity() > 60) {
            return new EventCapacityExceedsRoomCapacityException();
        }

        return null;
    }

    /***
     * checks the availability of rooms in the given time
     * @param sTime the start time of the event to be checked
     * @param eTime the end time of the event to be checked
     * @return Boolean - if the room is occupied at some point on the interval [sTime, eTime]
     */
    public Boolean isOccupiedDuringTime(LocalDateTime sTime, LocalDateTime eTime, int roomNumber) {

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

    private boolean checkSpeakersOccupied(List<UUID> speakers, LocalDateTime startTime, LocalDateTime endTime) {
        for (UUID speaker : speakers) {
            boolean speakerOccupied = speakerOccupied(startTime, endTime, speaker);
            if (speakerOccupied) {
                return true;
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
                notifyObservers(eventsToRemove, false);
                return eventsToRemove.get(0);
            }
        }
        return null;
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
     * Gets a list of events where a specific user is enrolled
     *
     * @param user - The user that we wish to fined the enrolled events of
     * @return A list of Event objects - All the events where <user> is enrolled
     */
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
     *
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
     *
     * @param attendee the User to be added to the event
     * @param event    the event for the user to join
     */
    private void addingUser(User attendee, Event event) {
        List<Event> eventsChanged = new ArrayList<>();
        event.addAttendee(attendee);
        eventsChanged.add(event);
        notifyObservers(eventsChanged, true);
    }

    /**
     * updates the information of an event based on the firebase updates for the data snapshot
     */
    public void updateStoredEvent(Event event) {

        Event.EventData eventData = event.getEventData();
        // Find event
        List<Event> eventsToChange = new ArrayList<>();

        for (Event e : events) {
            if (e.getId().equals(event.getId())) {
                e.set(eventData);
                eventsToChange.add(e);
                notifyObservers(eventsToChange, true);
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
     *
     * @param attendee the attendee that is to be removed from an event
     * @param event    the event the user is going to leave
     */
    private void removingUser(User attendee, Event event) {
        List<Event> eventsChanged = new ArrayList<>();
        event.removeAttendee(attendee);
        eventsChanged.add(event);
        notifyObservers(eventsChanged, true);
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
     *
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
     *
     * @param user the user for the sorted events
     * @return ArrayList of events with attendees
     */
    public ArrayList<Event> sortedEventsWithAttendees(User user) {
        return eventSortTime(getEventsWithAttendee(user));
    }

    /**
     * Gets the title of an event
     *
     * @param event the event to get the title of
     * @return String of the title of this event
     */
    public String getIndividualTitle(Event event) {
        return event.getEventTitle();
    }

    /**
     * gets the time of the event based on the given format
     *
     * @param event  the event we want the time of
     * @param format the format of the time we want in this case
     * @return the String version of the event
     */
    public String getIndividualTime(Event event, String format) {
        return event.getEventTime().format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * Gets the room of the event
     *
     * @param event the event of the room we want
     * @return String of the room number
     */
    public String getIndividualRoom(Event event) {
        return String.valueOf(event.getEventRoom());
    }

    /**
     * The capacity of the event given
     *
     * @param event the event we want to get the capacity of
     * @return String value of the capacity
     */
    public String getIndividualCapacity(Event event) {
        return String.valueOf(event.getEventCapacity());
    }

    /**
     * gets The number of users enrolled in an event
     *
     * @param event the event we want to get the number of users enrolled in
     * @return String value of the enrollment number
     */
    public String getIndividualEnrolledNumber(Event event) {
        return String.valueOf(event.getEventEnrolledNumber());
    }

    /**
     * The type of event that is being passed in
     *
     * @param event the event we want the type of
     * @return String value of the type of event
     */
    public String getIndividualType(Event event) {
        return String.valueOf(event.getEventType().toString());
    }

    /**
     * Returns on if the event is VIP only
     *
     * @param event the event we want to check if it is VIP only
     * @return String value of the VIP only message
     */
    public String getIndividualVIP(Event event) {
        return String.valueOf(event.getViponly());
    }

    /**
     * Changes the event capacity of the event passed in
     *
     * @param eventTitle         the title of the event as a string
     * @param newCapacity        the capacity of the event we want to change to
     * @param changeFromDatabase boolean on changes from database
     */
    public void changeEventCapacity(String eventTitle, int newCapacity, boolean changeFromDatabase) {
        List<Event> eventsToChange = new ArrayList<>();
        Event event = getEvent(eventTitle);
        event.setEventCapacity(newCapacity);
        eventsToChange.add(event);
        notifyObservers(eventsToChange, true);
    }

    /**
     * gets a list of events the user passed in is hosting
     *
     * @param id the user that is hosting the returned events
     * @return A list of strings of the event user u is hosting
     */
    public List<String> listOfEventsHosting(UUID id) {
        //returns a list of the events a presenter is hosting
        List<String> theList = new ArrayList<>();
        for (Event e : events) {
            if (e.getSpeakerId().contains(id)) {
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
     * Checks if this instance of the manager contains a given event
     *
     * @param e - the event we wish to check
     * @return a boolean - whether or not the event is being kept track of
     * by this instance of EventManager
     */
    public boolean isEventInManager(Event e) {
        return events.contains(e);
    }

    /**
     * Helper method that determines if the speaker given is occupied during the given start time and
     * end time
     *
     * @param sDateTime the start time to be checked for the speaker
     * @param eDateTime the end time to be checked for the speaker
     * @param speaker   the speaker to be checked if bust
     * @return a boolean TRUE if occupied during timeframe. FALSE otherwise
     */
    public boolean speakerOccupied(LocalDateTime sDateTime, LocalDateTime eDateTime, UUID speaker) {
        for (String e : listOfEventsHosting(speaker)) {
            Event eventHosting = getEvent(e);
            LocalDateTime startTime = eventHosting.getEventTime();
            LocalDateTime endTime = eventHosting.getEventETime();

            if (sDateTime.isBefore(startTime) && eDateTime.isAfter(startTime) |
                    (sDateTime.isBefore(endTime) && eDateTime.isAfter(endTime)) |
                    (sDateTime.isAfter(startTime) && eDateTime.isBefore(endTime))
            ) {
                return true;
            }
        }
        return false;
    }
}
