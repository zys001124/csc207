package gateways.snapshotreaders;

import com.google.firebase.database.DataSnapshot;
import entities.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * A DataSnapShotReader that can parse Event objects from DataSnapshots
 */
public class EventDataSnapshotReader implements DataSnapshotReader<Event> {

    /**
     * Gets the event entity from the given data snapshot from firebase
     *
     * @param dataSnapshot the snapshot from firebase that is updated automatically
     * @return Event entity of this conference system
     */
    @Override
    public Event getFromDataSnapshot(DataSnapshot dataSnapshot) {
        Event.EventData data = eventDataFromDataSnapshot(dataSnapshot);
        return Event.fromEventData(data);
    }

    /**
     * All of the data of the event from a given firebase data snapshot that is to be updated
     * automatically
     *
     * @param dataSnapshot the snapshot of the data to be passed
     * @return EventData of the entire EventManager
     */
    private Event.EventData eventDataFromDataSnapshot(DataSnapshot dataSnapshot) {

        Map eventMap = (Map<String, Object>) dataSnapshot.getValue();
        Event.EventData eventData = new Event.EventData();
        if (eventMap.containsKey("attendees")) {
            eventData.attendees = (Collection<String>) eventMap.get("attendees");
        } else {
            eventData.attendees = new ArrayList<>();
        }

        if (eventMap.containsKey("speakerIds")) {
            eventData.speakerIds = (Collection<String>) eventMap.get("speakerIds");
        } else {
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
