package useCaseClasses;

import entities.Event;
import entities.User;
import exceptions.*;
import observers.Observable;

import java.time.LocalDateTime;
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

    public void addEvent(String title, LocalDateTime startTime, LocalDateTime endTime,UUID id, UUID organizerId, List<UUID> speakerId,
                         List<UUID> attendees, int room, int capacity, boolean VIPonly) {

        Event e = new Event(title, startTime, endTime, id, organizerId, speakerId,
                attendees, room, capacity, VIPonly);

        addEvent(e);
    }

    public void addEventFromDatabase(Event.EventData data) {

        if(!eventExists(UUID.fromString(data.eventId))) {
            List<Event> eventsToAdd = new ArrayList<>();
            Event e = Event.fromEventData(data);
            eventsToAdd.add(e);
            events.addAll(eventsToAdd);
            notifyObservers(eventsToAdd, true, true);
        }
    }

    private boolean eventExists(UUID eventId) {
        for(Event event: events) {
            if(eventId.equals(event.getId())) {
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
        ArrayList<Integer> roomTaken= new ArrayList<>();

        for (Event temp : events) {
            LocalDateTime timeStart = temp.getEventTime();
            LocalDateTime timeEnd = temp.getEventETime();
            Integer roomNum = temp.getEventRoom();
            if (timeStart.isBefore(sTime) && timeEnd.isAfter(sTime)||
                    (timeStart.isBefore(eTime) && timeEnd.isAfter(eTime))||
                    (timeStart.isAfter(sTime) && timeEnd.isBefore((eTime)))
            ) {
                if (!roomTaken.contains(roomNum)){roomTaken.add(roomNum);}
            }
            if (roomTaken.size() == 6){break;}
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

    public void removeEventFromDataBase(UUID id) {

        List<Event> eventsToRemove = new ArrayList<>();
        for (Event event : events) {
            if (event.getId().equals(id)) {
                eventsToRemove.add(event);
                removeEvent(id);
            }
        }
        notifyObservers(eventsToRemove, false, true);
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
     * @param parsedInput The index of the event in the event list
     * @param attendee    The user(attendee) to be added to the event
     * @throws EventNotFoundException       if the entered index does not corresponds to a event
     * @throws NumberFormatException        if the input is not a number
     * @throws UserAlreadyEnrolledException if the user(attendee) was already enrolled in the event
     */
    public void addUserToEvent(int parsedInput, User attendee) throws EventNotFoundException,
            NumberFormatException, UserAlreadyEnrolledException, EventFullException, InvalidUserTypeException {
        if (parsedInput <= events.size() && parsedInput > 0) {
            Event e = events.get(parsedInput - 1);
            if(e.getViponly() && !attendee.getType().equals(User.UserType.VIP)){
                throw new InvalidUserTypeException(User.UserType.VIP, attendee.getType());
            }
            if (e.isFull()){
                throw new EventFullException(parsedInput);
            }
            if (e.hasAttendee(attendee.getId())) {
                throw new UserAlreadyEnrolledException();
            }
            List<Event> eventsChanged = new ArrayList<>();
            e.addAttendee(attendee);
            eventsChanged.add(e);
            notifyObservers(eventsChanged, true, false);
            return;
        }
        throw new EventNotFoundException(parsedInput);
    }

    public void addUserToEventFromDataBase(int parsedInput, User attendee) throws EventNotFoundException,
            NumberFormatException, UserAlreadyEnrolledException, EventFullException, InvalidUserTypeException {
        if (parsedInput <= events.size() && parsedInput > 0) {
            Event e = events.get(parsedInput - 1);
            if(e.getViponly() && !attendee.getType().equals(User.UserType.VIP)){
                throw new InvalidUserTypeException(User.UserType.VIP, attendee.getType());
            }
            if (e.isFull()){
                throw new EventFullException(parsedInput);
            }
            if (e.hasAttendee(attendee.getId())) {
                throw new UserAlreadyEnrolledException();
            }
            List<Event> eventsChanged = new ArrayList<>();
            e.addAttendee(attendee);
            eventsChanged.add(e);
            notifyObservers(eventsChanged, true, true);
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

            Event e = events.get(parsedInput - 1);
            List<Event> eventsChanged = new ArrayList<>();
            e.removeAttendee(attendee);
            eventsChanged.add(e);
            notifyObservers(eventsChanged, true, false);
            return;
        }
        throw new EventNotFoundException(parsedInput);
    }

    public void removeUserFromEventFromDatabase(int parsedInput, User attendee) throws EventNotFoundException,
            NumberFormatException, UserNotEnrolledInEventException {
        if (parsedInput <= events.size() && parsedInput > 0) {
            if (!events.get(parsedInput - 1).hasAttendee(attendee.getId())) {
                throw new UserNotEnrolledInEventException();
            }

            Event e = events.get(parsedInput - 1);
            List<Event> eventsChanged = new ArrayList<>();
            e.removeAttendee(attendee);
            eventsChanged.add(e);
            notifyObservers(eventsChanged, true, true);
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
            if (e.getSpeakerId().contains(u.getId())) {
                theList.add(e.getEventTitle());
            }
        }
        return theList;
    }

    public boolean eventTitleExists(String title) {
        for(Event e: events) {
            if(e.getEventTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }
}
