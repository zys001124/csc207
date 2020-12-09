package gateways;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import entities.Event;
import gateways.snapshotreaders.DataSnapshotReader;
import gateways.snapshotreaders.EventDataSnapshotReader;
import useCaseClasses.EventManager;

import java.util.List;

/**
 * A gateway for handling the saving/loading of Event data from
 * a Firebase Realtime Database
 */
public class EventGateway extends FirebaseGateway<Event> {

    private final EventManager eventManager;

    /**
     * The constructor for an EventGateway object
     *
     * @param em       - The EventManager the program is using
     * @param database - A FirebaseDatabase object that will be used to create
     *                 a reference to the directory in the database holding
     *                 Event information
     */
    public EventGateway(EventManager em, FirebaseDatabase database) {
        super("Events", database);
        eventManager = em;
    }

    /**
     * This method is called when a piece of information is added under
     * the directory in the database that holds Event information
     *
     * @param dataSnapshot - an object containing the information that has been added
     * @param s            - the name of the node before being added (it will be null since the node didnt
     *                     exist before)
     */
    @Override
    protected void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Event event = snapshotReader.getFromDataSnapshot(dataSnapshot);

        try {
            if (!eventManager.isEventInManager(event)) eventManager.addEvent(event);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * This method is called when a piece of information is changed under
     * the directory in the database that holds Event information
     *
     * @param dataSnapshot - an object containing the information that has been changed
     * @param s            - the name of the node before being added
     */
    @Override
    protected void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Event e = snapshotReader.getFromDataSnapshot(dataSnapshot);
        if (!eventManager.isEventInManager(e)) eventManager.updateStoredEvent(e);
    }

    /**
     * This method is called when a piece of information is removed under
     * the directory in the database that holds Event information
     *
     * @param dataSnapshot - an object containing the information that has been removed
     */
    @Override
    protected void onChildRemoved(DataSnapshot dataSnapshot) {
        Event e = snapshotReader.getFromDataSnapshot(dataSnapshot);
        if (eventManager.isEventInManager(e)) eventManager.removeEvent(e.getId());
    }

    /**
     * This method pushes the information of a list of Events to
     * the Firebase Realtime Database
     *
     * @param events - the list of Events to save information of
     */
    @Override
    public void pushEntities(List<Event> events) {
        for (Event event : events) {
            Event.EventData eventData = event.getEventData();
            databaseReference.child(event.getId().toString()).setValueAsync(eventData);
            databaseReference.child(event.getId().toString()).child("attendees").setValueAsync(eventData.attendees);
            databaseReference.child(event.getId().toString()).child("speakerIds").setValueAsync(eventData.speakerIds);
        }
    }

    /**
     * This method removes the information of a list of Events to
     * the Firebase Realtime Database
     *
     * @param events - the list of Events to remove information of
     */
    @Override
    public void removeEntities(List<Event> events) {
        for (Event event : events) {
            databaseReference.child(event.getId().toString()).removeValueAsync();
        }
    }

    /**
     * Gets the snapshotReader
     *
     * @return an EventDataSnapshotReader
     */
    @Override
    protected DataSnapshotReader<Event> getSnapshotReader() {
        if (snapshotReader == null) {
            snapshotReader = new EventDataSnapshotReader();
        }
        return snapshotReader;
    }
}
