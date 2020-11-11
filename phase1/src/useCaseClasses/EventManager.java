package useCaseClasses;

import entities.Event;
import entities.User;
import exceptions.EventNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EventManager {

    private List<Event> events;

    public EventManager(List<Event> events) {
        this.events = events;

    }
    public boolean addEvent(Event e){
        LocalDateTime time = e.getEventTime();
        for (Event temp: events){
            LocalDateTime timeGot = temp.getEventTime();
            if (timeGot.equals(time)){
                if (temp.getEventRoom() == e.getEventRoom()){
                    return false;
                }
            }
        }
        events.add(e);
        return true;
    }

    public boolean availabilityInTime(LocalDateTime time){
        int num = 0;
        for (Event temp: events) {
            LocalDateTime timeGot = temp.getEventTime();
            if (timeGot.equals(time)) {
                num++;
            }
        }
        return num == 6;
    }

    public Event removeEvent(int i){
        return events.remove(i);
    }

    public Event removeEvent(UUID id){
        for(Event event: events){
            int index = events.indexOf(event);
            if(event.getId().equals(id)){
                return events.remove(index);
            }
        }
        return null;
    }


    public Event getEvent(UUID id) {
        for(Event event: events) {
            if(event.getId().equals(id)) {
                return event;
            }
        }
        return null;
    }

    public Event getEvent(String event){
        for(Event e: events) {
            if(e.getEventTitle().equals(event)) {
                return e;
            }
        }
        return null;
    }

    public List<Event> getEvents(){
        return events;
    }

    public boolean hasOrganizedEvent(User u, Event e){
        return e.getOrganizerId().equals(u.getId());
    }

    public boolean isSpeakerForEvent(User u, Event e) {
        return e.getSpeakerId() == u.getId();
    }

    public void addUserToEvent(int parsedInput, User attendee) throws EventNotFoundException, NumberFormatException {
        if (parsedInput <= events.size() && parsedInput > 0) {
            this.events.get(parsedInput - 1).addAttendee(attendee);
            return;
        }
        throw new EventNotFoundException(parsedInput);
    }

    public List<String> ListOfEventsHosting(User u) {
        //returns a list of the events a presenter is hosting
        List<String> theList = new ArrayList<>();
        for(Event e: events){
            if(e.getSpeakerId() == u.getId()){
                theList.add(e.getEventTitle());
            }
        }
        return theList;
    }



}
