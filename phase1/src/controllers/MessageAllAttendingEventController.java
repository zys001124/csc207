package controllers;

import entities.Event;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

import java.util.UUID;

public class MessageAllAttendingEventController {

    private UserManager userManager;
    private MessageManager messageManager;
    private EventManager eventManager;
    private UUID eventId;

    public MessageAllAttendingEventController(UserManager am, MessageManager mm, EventManager em, UUID eventId) {
        userManager = am;
        messageManager = mm;
        eventManager = em;
        this.eventId = eventId;
    }

    public void sendMessage(String message)
    {
        messageManager.messageAllAttendingEvent(message, eventManager.getEvent(eventId),
                userManager.getCurrentlyLoggedIn());
    }
}
