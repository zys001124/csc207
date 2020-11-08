package useCaseClasses;

import entities.Event;
import entities.Organizer;

import java.util.ArrayList;
import java.util.List;

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

    public List<Event> getEvents(){
        return events;
    }

    public boolean isHostingRightEvent(Organizer o, Event e){
        List<Event> hosting = o.getEventsOrganized();
        for(Event event : hosting) {
            if (event == e) {
                return true;
            }
        }
        return false;
    }
}
