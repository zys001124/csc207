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
        ArrayList<Boolean> eventInTime = new ArrayList<>(6);
        for (Event temp: events){
            LocalDateTime timeGot = temp.getEventTime();
            if (timeGot.equals(time)){
                eventInTime.add(temp.getEventRoom(),true);
            }
        }
        int roomNum = e.getEventRoom();
        if (eventInTime.get(roomNum)){
            return false;
        }else{
            events.add(e);
            return true;
        }

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

    public List<Event> getEvents(){
        return events;
    }

    public boolean hasOrganizedEvent(User u, Event e){
        return e.getOrganizerId().equals(u.getId());
    }

    public boolean isSpeakerForEvent(User u, Event e) {
        return e.getSpeakerId() == u.getId();
    }

    public void addUserToEvent(String eventName, User attendee) throws EventNotFoundException{
        for(Event event: events){
            if(event.getEventTitle().equals(eventName)){
                event.addAttendee(attendee);
            }
        }
        throw new EventNotFoundException(eventName);
    }

}
