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

    /**
     * Getter for userManager
     *
     * @return the userManager field
     */
    public UserManager getUserManager() {
        return userManager;
    }

    /**
     * Getter for messageManager
     *
     * @return the messageManager field
     */
    public MessageManager getMessageManager() {
        return messageManager;
    }

    /**
     * Getter for eventManager
     *
     * @return the eventManager field
     */
    public EventManager getEventManager() {
        return eventManager;
    }

    /**
     * Adds an UpdateDatabaseObserver<User> to the userManager
     *
     * @param userManagerObserver - the Observer to add
     */
    public void addUserManagerDatabaseObserver(UpdateDatabaseObserver<User> userManagerObserver) {
        userManager.addObserver(userManagerObserver);
    }

    /**
     * Adds an UpdateDatabaseObserver<Message> to the messageManager
     *
     * @param messageManagerObserver - the Observer to add
     */
    public void addMessageManagerDatabaseObserver(UpdateDatabaseObserver<Message> messageManagerObserver) {
        messageManager.addObserver(messageManagerObserver);
    }

    /**
     * Adds an UpdateDatabaseObserver<Event> to the eventManager
     *
     * @param eventManagerObserver - the Observer to add
     */
    public void addEventManagerDatabaseObserver(UpdateDatabaseObserver<Event> eventManagerObserver) {
        eventManager.addObserver(eventManagerObserver);
    }
}
