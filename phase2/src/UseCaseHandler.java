import gateways.FirebaseGateway;
import observers.EventManagerObserver;
import observers.MessageManagerObserver;
import observers.UserManagerObserver;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

public class UseCaseHandler {

    // Managers
    private UserManager userManager;
    private MessageManager messageManager;
    private EventManager eventManager;

    // Observers
    private UserManagerObserver userManagerObserver;
    private MessageManagerObserver messageManagerObserver;
    private EventManagerObserver eventManagerObserver;

    private FirebaseGateway fbg;

    public UseCaseHandler() {

        userManager = new UserManager();
        messageManager = new MessageManager();
        eventManager = new EventManager();

        fbg = new FirebaseGateway(userManager, eventManager, messageManager);

        userManagerObserver = new UserManagerObserver(fbg);
        messageManagerObserver = new MessageManagerObserver(fbg);
        eventManagerObserver = new EventManagerObserver(fbg);

        userManager.addObserver(userManagerObserver);
        messageManager.addObserver(messageManagerObserver);
        eventManager.addObserver(eventManagerObserver);
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
