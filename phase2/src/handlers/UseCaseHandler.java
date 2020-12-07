package handlers;

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

public class UseCaseHandler {

    // Managers
    private final UserManager userManager;
    private final MessageManager messageManager;
    private final EventManager eventManager;

    // Observers
    private final UpdateDatabaseObserver<User> userUpdateDatabaseObserver;
    private final UpdateDatabaseObserver<Message> messageUpdateDatabaseObserver;
    private final UpdateDatabaseObserver<Event> eventUpdateDatabaseObserver;

    private final UserGateway userGateway;
    private final MessageGateway messageGateway;
    private final EventGateway eventGateway;

    public UseCaseHandler() {

        userManager = new UserManager();
        messageManager = new MessageManager();
        eventManager = new EventManager();

//        fbg = new FirebaseGatewayOld(userManager, eventManager, messageManager);
        userGateway = new UserGateway(userManager);
        messageGateway = new MessageGateway(messageManager);
        eventGateway = new EventGateway(eventManager);

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
