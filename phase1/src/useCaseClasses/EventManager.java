package useCaseClasses;

import entities.Event;
import entities.User;
import exceptions.EventNotFoundException;
import exceptions.UserAlreadyEnrolledException;
import exceptions.UserNotEnrolledInEventException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents an eventManager that can modify events of the conference
 */
public class EventManager {

    private final List<Event> events;

    /***
     * Create an EventManager with given list of events
     * @param events list of events
     */
    public EventManager(List<Event> events) {
        this.events = events;
    }

    /***
     *add an given event if the conditions check out
     * @param e the event that is check if it could be added.
     * @return the boolean if the event is added to events.
     */
    public boolean addEvent(Event e) {
        LocalDateTime time = e.getEventTime();
        LocalDateTime timeEnd = time.plusHours(1);
        for (Event temp : events) {
            LocalDateTime timeGot = temp.getEventTime();
            LocalDateTime timeAdded = timeGot.plusHours(1);
            if ((timeGot.isBefore(time) && timeAdded.isAfter(time)) ||
                    (timeGot.isBefore(timeEnd) && timeAdded.isAfter(timeEnd))) {
                if (temp.getEventRoom() == e.getEventRoom()) {
                    return false;
                }
            }
        }
        events.add(e);
        return true;
    }

    /***
     * checks the availability of rooms in the given time
     * @param time the time to be check
     * @return boolean if the
     */
    public boolean availabilityInTime(LocalDateTime time) {
        int num = 0;
        LocalDateTime timeEnd = time.plusHours(1);
        for (Event temp : events) {
            LocalDateTime timeGot = temp.getEventTime();
            LocalDateTime timeAdded = timeGot.plusHours(1);
            if (timeGot.isBefore(time) && timeAdded.isAfter(time)) {
                num++;
            } else if (timeGot.isBefore(timeEnd) && timeAdded.isAfter(timeEnd)) {
                num++;
            }
        }
        return num >= 6;
    }

    /**
     * removes an event in the given event list based on the index given for the list
     *
     * @param i the index of the event to be removed from the list
     * @return the event that will be removed
     */
    public Event removeEvent(int i) {
        return events.remove(i);
    }

    /**
     * removes an event in the given event list where the event is given by its UUID
     * returns null if event can't be found.
     *
     * @param id The UUID of the event to be found
     * @return the event that is being removed or null if it can't be found.
     */
    public Event removeEvent(UUID id) {
        for (Event event : events) {
            int index = events.indexOf(event);
            if (event.getId().equals(id)) {
                return events.remove(index);
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
        return e.getSpeakerId() == u.getId();
    }

    /**
     * Find the event in the list based on user input index and add this user to the event
     *
     * @param parsedInput The index of the event in the event list
     * @param attendee    The user(attendee) to be added to the event
     * @throws EventNotFoundException       if the entered index does not corresponds to a event
     * @throws NumberFormatException        if the input is not a number
     * @throws UserAlreadyEnrolledException if the user(attendee) was already enrolled in the event
     */
    public void addUserToEvent(int parsedInput, User attendee) throws EventNotFoundException,
            NumberFormatException, UserAlreadyEnrolledException {
        if (parsedInput <= events.size() && parsedInput > 0) {
            Event e = events.get(parsedInput - 1);
            if (e.hasAttendee(attendee.getId())) {
                throw new UserAlreadyEnrolledException();
            }
            e.addAttendee(attendee);
            return;
        }
        throw new EventNotFoundException(parsedInput);
    }

    /**
     * Find the event in the list based on user input index and remove this user from the event
     *
     * @param parsedInput The index of the event in the event list
     * @param attendee    The user(attendee) to be removed from the event
     * @throws EventNotFoundException          if the entered index does not corresponds to a event
     * @throws NumberFormatException           if the input is not a number
     * @throws UserNotEnrolledInEventException if the user(attendee) was never enrolled in the event
     *                                         in the first place
     */
    public void removeUserFromEvent(int parsedInput, User attendee) throws EventNotFoundException,
            NumberFormatException, UserNotEnrolledInEventException {
        if (parsedInput <= events.size() && parsedInput > 0) {
            if (!events.get(parsedInput - 1).hasAttendee(attendee.getId())) {
                throw new UserNotEnrolledInEventException();
            }

            events.get(parsedInput - 1).removeAttendee(attendee);
            return;
        }
        throw new EventNotFoundException(parsedInput);
    }

    /**
     * This method sorts the event list in the order of date and time
     *
     * @return An arraylist with events sorts in order of datetime
     */
    public ArrayList<Event> eventSortTime() {
        ArrayList<Event> result = new ArrayList<>(events);

        //Insertion Sort
        for (int i = 1; i < result.size(); i++) {
            Event cur = result.get(i);
            int j = i - 1;
            while (j >= 0 && result.get(j).getEventTime().isAfter(cur.getEventTime())) {
                result.set(j + 1, result.get(j));
                j = j - 1;
            }
            result.set(j + 1, cur);
        }

        return result;
    }

    public List<String> listOfEventsHosting(User u) {
        //returns a list of the events a presenter is hosting
        List<String> theList = new ArrayList<>();
        for (Event e : events) {
            if (e.getSpeakerId().equals(u.getId())) {
                theList.add(e.getEventTitle());
            }
        }
        return theList;
    }


}
