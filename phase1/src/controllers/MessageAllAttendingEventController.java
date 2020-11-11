package controllers;

import UI.ConsoleView;
import entities.Event;
import entities.User;
import presenters.MessageAllAttendingEventPresenter;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

import java.util.UUID;

public class MessageAllAttendingEventController {

    private UserManager userManager;
    private MessageManager messageManager;
    private EventManager eventManager;
    private UUID eventId;
    private MessageAllAttendingEventPresenter presenter;
    private User user;

    public MessageAllAttendingEventController(UserManager am, MessageManager mm, EventManager em, UUID eventId, MessageAllAttendingEventPresenter presenter, User user) {
        userManager = am;
        messageManager = mm;
        eventManager = em;
        //this.eventId = eventId;
        this.presenter = presenter;
        this.user = user; //Is this needed?

    }

    public void sendMessage(String message, String event)
    {
        messageManager.messageAllAttendingEvent(message, eventManager.getEvent(event),
                userManager.getCurrentlyLoggedIn().getId());
    }

    public ConsoleView.ConsoleViewType getNextScreen(String name, String message) {
        if (name.equals("back")) {
            return ConsoleView.ConsoleViewType.MAIN_MENU;
        }
        else{
            FindEvent verify = verifyEvent(name);
            if(verify == FindEvent.FAIL){
                presenter.setInputResponse("UUID for event not found for your presentation. Please try again.");
                return ConsoleView.ConsoleViewType.MESSAGE_ALL_ATTENDING_EVENT;
            }
            else{
                sendMessage(message, name);
                presenter.setInputResponse("You have successfully messaged all the users going to this event.");
                return ConsoleView.ConsoleViewType.MAIN_MENU;
            }
        }

    }

    public FindEvent verifyEvent(String name){
        //Should we be using userManager.getCurrentlyLoggedIn() or should we just use the user field?
        if(eventManager.ListOfEventsHosting(userManager.getCurrentlyLoggedIn()).contains(name)){
            return FindEvent.SUCCESS;
        }
        else{
            return FindEvent.FAIL;
        }
    }

    public enum FindEvent{
        SUCCESS,
        FAIL
    }


}
