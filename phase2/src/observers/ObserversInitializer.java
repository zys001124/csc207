package observers;

import entities.Event;
import entities.Message;
import entities.User;
import gateways.EventGateway;
import gateways.MessageGateway;
import gateways.UserGateway;

/**
 * An object that creates, and holds the UpdateDatabaseObserver objects
 * necessary for this program to run
 */
public class ObserversInitializer {

    // Observers
    private final UpdateDatabaseObserver<User> userUpdateDatabaseObserver;
    private final UpdateDatabaseObserver<Message> messageUpdateDatabaseObserver;
    private final UpdateDatabaseObserver<Event> eventUpdateDatabaseObserver;

    /**
     * The constructor for ObserversInitializer
     *
     * @param ug - The FirebaseGateway object responsible for keeping user information synced
     * @param mg - The FirebaseGateway object responsible for keeping message information synced
     * @param eg - The FirebaseGateway object responsible for keeping event information synced
     */
    public ObserversInitializer(UserGateway ug, MessageGateway mg, EventGateway eg) {
        userUpdateDatabaseObserver = new UpdateDatabaseObserver(ug);
        messageUpdateDatabaseObserver = new UpdateDatabaseObserver(mg);
        eventUpdateDatabaseObserver = new UpdateDatabaseObserver(eg);
    }

    /**
     * Getter for userUpdateDatabaseObserver
     *
     * @return userUpdateDatabaseObserver
     */
    public UpdateDatabaseObserver<User> getUserUpdateDatabaseObserver() {
        return userUpdateDatabaseObserver;
    }

    /**
     * Getter for messageUpdateDatabaseObserver
     *
     * @return messageUpdateDatabaseObserver
     */
    public UpdateDatabaseObserver<Message> getMessageUpdateDatabaseObserver() {
        return messageUpdateDatabaseObserver;
    }

    /**
     * Getter for eventUpdateDatabaseObserver
     *
     * @return eventUpdateDatabaseObserver
     */
    public UpdateDatabaseObserver<Event> getEventUpdateDatabaseObserver() {
        return eventUpdateDatabaseObserver;
    }

}
