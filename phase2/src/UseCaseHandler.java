import gateways.FirebaseGateway;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

public class UseCaseHandler {

    // Managers
    private UserManager userManager;
    private MessageManager messageManager;
    private EventManager eventManager;

    private FirebaseGateway fbg;

    public UseCaseHandler() {

        userManager = new UserManager();
        messageManager = new MessageManager();
        eventManager = new EventManager();

        fbg = new FirebaseGateway(userManager, eventManager, messageManager);
        fbg.loadEntities();
    }

    public void saveEntities() {
        fbg.pushUsers();
        fbg.pushEvents();
        fbg.pushMessages();
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
