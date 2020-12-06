package gateways;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import entities.Event;
import useCaseClasses.EventManager;

import java.util.*;

public class EventGateway extends FirebaseGateway<Event>{

    private EventManager eventManager;

    public EventGateway(EventManager em) {
        super("Events");
        eventManager = em;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Event.EventData data = eventDataFromDataSnapshot(dataSnapshot);
        eventManager.addEventFromDatabase(data);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Event.EventData data = eventDataFromDataSnapshot(dataSnapshot);
        eventManager.updateEventFromDatabase(data);
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        Event.EventData data = eventDataFromDataSnapshot(dataSnapshot);
        eventManager.removeEventFromDataBase(UUID.fromString(data.eventId));
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public void pushEntities(List<Event> events) {
        for(Event event: events) {
            Event.EventData eventData = event.getEventData();
            databaseReference.child(event.getId().toString()).setValueAsync(eventData);
            databaseReference.child(event.getId().toString()).child("attendees").setValueAsync(eventData.attendees);
            databaseReference.child(event.getId().toString()).child("speakerIds").setValueAsync(eventData.speakerIds);
        }
    }

    public void removeEntities(List<Event> events) {
        for(Event event: events) {
            databaseReference.child(event.getId().toString()).removeValueAsync();
        }
    }

    private Event.EventData eventDataFromDataSnapshot(DataSnapshot dataSnapshot) {

        Map eventMap= (Map<String, Object>) dataSnapshot.getValue();
        Event.EventData eventData = new Event.EventData();
        if(eventMap.containsKey("attendees")) {
            eventData.attendees = (Collection<String>)eventMap.get("attendees");
        }
        else {
            eventData.attendees = new ArrayList<>();
        }

        if(eventMap.containsKey("speakerIds")) {
            eventData.speakerIds = (Collection<String>)eventMap.get("speakerIds");
        }
        else {
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
