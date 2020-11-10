package useCaseClasses;

import entities.Event;
import entities.User;

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

    public void addEvent(Event e){
        events.add(e);
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

}
