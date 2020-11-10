package useCaseClasses;

import entities.Event;
import entities.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EventManager {

    private List<Event> events;

    public EventManager(){
        events = new ArrayList<>();
    }

    public EventManager(List<Event> events){
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

    public Event removeEvent(int i){
        return events.remove(i);
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
        return e.getOrganizerId() == u.getId();
    }

    public boolean isSpeakerForEvent(User u, Event e) {
        return e.getSpeakerId() == u.getId();
    }

}
