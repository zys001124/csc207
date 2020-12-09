package useCaseClasses;

import entities.Event;
import entities.Message;
import entities.User;
import observers.UpdateDatabaseObserver;

/**
 * An object responsible for initializing the use case classes
 */
public class UseCaseHolder {

    // Managers
    private final UserManager userManager;
    private final MessageManager messageManager;
    private final EventManager eventManager;

    /**
     * The constructor for a UseCaseInitializer object
     * <p>
     * Sets up use case classes, FirebaseGateways, and observers so
     * that each use case class has the correct program information the entire
     * time the program is run
     */
    public UseCaseHolder() {

        userManager = new UserManager();
        messageManager = new MessageManager();
        eventManager = new EventManager();
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

    public void addUserManagerDatabaseObserver(UpdateDatabaseObserver<User> userManagerObserver) {
        userManager.addObserver(userManagerObserver);
    }

    public void addMessageManagerDatabaseObserver(UpdateDatabaseObserver<Message> messageManagerObserver) {
        messageManager.addObserver(messageManagerObserver);
    }

    public void addEventManagerDatabaseObserver(UpdateDatabaseObserver<Event> eventManagerObserver) {
        eventManager.addObserver(eventManagerObserver);
    }
}
