package gateways;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import entities.Event;
import useCaseClasses.EventManager;

import java.util.List;

public class EventGateway extends FirebaseGateway<Event> {

    private final EventManager eventManager;

    public EventGateway(EventManager em) {
        super("Events");
        eventManager = em;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        eventManager.addEventFromDataSnapshot(dataSnapshot);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        eventManager.updateEventFromDataSnapshot(dataSnapshot);
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        eventManager.removeEventFromDataSnapshot(dataSnapshot);
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    public void pushEntities(List<Event> events) {
        for (Event event : events) {
            Event.EventData eventData = event.getEventData();
            databaseReference.child(event.getId().toString()).setValueAsync(eventData);
            databaseReference.child(event.getId().toString()).child("attendees").setValueAsync(eventData.attendees);
            databaseReference.child(event.getId().toString()).child("speakerIds").setValueAsync(eventData.speakerIds);
        }
    }

    public void removeEntities(List<Event> events) {
        for (Event event : events) {
            databaseReference.child(event.getId().toString()).removeValueAsync();
        }
    }

}
