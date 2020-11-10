package controllers;

import UI.ConsoleView;
import exceptions.InvalidInputException;
import exceptions.UsernameAlreadyExistsException;
import useCaseClasses.EventManager;
import presenters.EventCancelPresenter;
import entities.Event;
import entities.User;
import useCaseClasses.UserManager;

import java.util.UUID;

public class EventCancelController {

    private EventManager Emanager;
    private EventCancelPresenter presenter;
    private UserManager Umanager;


    public EventCancelController(EventManager Emanager, EventCancelPresenter presenter, UserManager Umanager){
        this.Emanager = Emanager;
        this.presenter = presenter;
        this.Umanager = Umanager;
    }

    public ConsoleView.ConsoleViewType getNextScreen(String input){

        if(input.equals("back")) {
            return ConsoleView.ConsoleViewType.MAIN_MENU;
        }

        String[] userIdandEventId = input.split(" ");
        if(userIdandEventId.length != 2) {
            presenter.setInputResponse("Expecting two, and only two inputs. Try again.");
            return ConsoleView.ConsoleViewType.CANCEL_EVENT;
        }

        for(String s: userIdandEventId){
            if(!isUUID(s)){
                System.out.println("Incorrect ID. Try again.");
                return ConsoleView.ConsoleViewType.CANCEL_EVENT;
            }
        }

        UUID eventId = UUID.fromString(userIdandEventId[1]);
        UUID userId = UUID.fromString(userIdandEventId[0]);

        if(Emanager.getEvent(eventId)==null){
            presenter.setInputResponse("This event does not exist. Try again.");
            return ConsoleView.ConsoleViewType.CANCEL_EVENT;
        }

        Event currentEvent = Emanager.getEvent(eventId);
        User currentUser = Umanager.getUser(userId);

        if(!Emanager.hasOrganizedEvent(currentUser, currentEvent)){
            presenter.setInputResponse("This event is not organized by you. Try again.");
            return ConsoleView.ConsoleViewType.CANCEL_EVENT;
        }


        Emanager.removeEvent(eventId);
        presenter.setInputResponse("Event Canceled successfully");

        return ConsoleView.ConsoleViewType.CANCEL_EVENT;
    }

    private boolean isUUID(String string) {
        try {
            UUID.fromString(string);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}
