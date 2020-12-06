package handlers;

import entities.Event;
import entities.Message;
import entities.User;
import gateways.EventGateway;
import gateways.MessageGateway;
import gateways.UserGateway;
import observers.ManagerObserver;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

public class UseCaseHandler {

    // Managers
    private UserManager userManager;
    private MessageManager messageManager;
    private EventManager eventManager;

    // Observers
    private ManagerObserver<User> userManagerObserver;
    private ManagerObserver<Message> messageManagerObserver;
    private ManagerObserver<Event> eventManagerObserver;

    private UserGateway userGateway;
    private MessageGateway messageGateway;
    private EventGateway eventGateway;

    public UseCaseHandler() {

        userManager = new UserManager();
        messageManager = new MessageManager();
        eventManager = new EventManager();

//        fbg = new FirebaseGatewayOld(userManager, eventManager, messageManager);
        userGateway = new UserGateway(userManager);
        messageGateway = new MessageGateway(messageManager);
        eventGateway = new EventGateway(eventManager);

        userManagerObserver = new ManagerObserver(userGateway);
        messageManagerObserver = new ManagerObserver(messageGateway);
        eventManagerObserver = new ManagerObserver(eventGateway);

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
