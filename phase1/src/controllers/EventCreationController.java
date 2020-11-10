package controllers;

import entities.Event;
import useCaseClasses.EventManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventCreationController {
    public EventManager manager;

    public EventCreationController(EventManager manager){this.manager = manager;}

    public addResult createEvent(String title, LocalDateTime time, UUID id, UUID organizerId,
                            UUID speakerId, List<UUID> attendees, Integer room){
        Event eventCreate = new Event(title, time, id, organizerId, speakerId, attendees, room);
        if (manager.addEvent(eventCreate)){
            return addResult.Success;
        }
        return addResult.Use_Different_Room ;
    }

    public enum addResult{
        Success,
        Use_Different_Room
    }


}
