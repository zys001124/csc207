package holders;

import com.google.firebase.database.FirebaseDatabase;
import entities.Event;
import entities.Message;
import entities.User;
import gateways.EventGateway;
import gateways.MessageGateway;
import gateways.UserGateway;
import observers.UpdateDatabaseObserver;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

/**
 * An object responsible for initializing the use case classes
 */
public class UseCaseInitializer {

    // Managers
    private final UserManager userManager;
    private final MessageManager messageManager;
    private final EventManager eventManager;

    // Observers
    private final UpdateDatabaseObserver<User> userUpdateDatabaseObserver;
    private final UpdateDatabaseObserver<Message> messageUpdateDatabaseObserver;
    private final UpdateDatabaseObserver<Event> eventUpdateDatabaseObserver;

    // Gateways
    private final UserGateway userGateway;
    private final MessageGateway messageGateway;
    private final EventGateway eventGateway;

    /**
     * The constructor for a UseCaseInitializer object
     * <p>
     * Sets up use case classes, FirebaseGateways, and observers so
     * that each use case class has the correct program information the entire
     * time the program is run
     */
    public UseCaseInitializer() {

        userManager = new UserManager();
        messageManager = new MessageManager();
        eventManager = new EventManager();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        userGateway = new UserGateway(userManager, database);
        messageGateway = new MessageGateway(messageManager, database);
        eventGateway = new EventGateway(eventManager, database);

        userUpdateDatabaseObserver = new UpdateDatabaseObserver(userGateway);
        messageUpdateDatabaseObserver = new UpdateDatabaseObserver(messageGateway);
        eventUpdateDatabaseObserver = new UpdateDatabaseObserver(eventGateway);

        userManager.addObserver(userUpdateDatabaseObserver);
        messageManager.addObserver(messageUpdateDatabaseObserver);
        eventManager.addObserver(eventUpdateDatabaseObserver);
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }
}
