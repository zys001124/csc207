package observers;

import entities.Event;
import entities.Message;
import entities.User;
import gateways.EventGateway;
import gateways.MessageGateway;
import gateways.UserGateway;

public class ObserversInitializer {

    // Observers
    private final UpdateDatabaseObserver<User> userUpdateDatabaseObserver;
    private final UpdateDatabaseObserver<Message> messageUpdateDatabaseObserver;
    private final UpdateDatabaseObserver<Event> eventUpdateDatabaseObserver;

    public ObserversInitializer(UserGateway ug, MessageGateway mg, EventGateway eg) {
        userUpdateDatabaseObserver = new UpdateDatabaseObserver(ug);
        messageUpdateDatabaseObserver = new UpdateDatabaseObserver(mg);
        eventUpdateDatabaseObserver = new UpdateDatabaseObserver(eg);
    }

    public UpdateDatabaseObserver<User> getUserUpdateDatabaseObserver() {
        return userUpdateDatabaseObserver;
    }

    public UpdateDatabaseObserver<Message> getMessageUpdateDatabaseObserver() {
        return messageUpdateDatabaseObserver;
    }

    public UpdateDatabaseObserver<Event> getEventUpdateDatabaseObserver() {
        return eventUpdateDatabaseObserver;
    }

}
