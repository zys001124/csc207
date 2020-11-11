package controllers;

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

    public InputProcessResult getNextScreen(String input) {

        if (input.equals("back")) {
            return InputProcessResult.BACK;
        }

        String[] userIdandEventId = input.split(" ");
        if (userIdandEventId.length != 2) {
            presenter.setInputResponse("Expecting two, and only two inputs. Try again.");
            return InputProcessResult.INVALID_INPUT;
        }

        for (String s : userIdandEventId) {
            if (!isUUID(s)) {
                System.out.println("Incorrect ID. Try again.");
                return InputProcessResult.INCORRECT_EVENT_ID;
            }
        }

        UUID eventId = UUID.fromString(userIdandEventId[1]);
        UUID userId = UUID.fromString(userIdandEventId[0]);

        if (Emanager.getEvent(eventId) == null) {
            presenter.setInputResponse("This event does not exist. Try again.");
            return InputProcessResult.EVENT_DOES_NOT_EXIST;
        }

        Event currentEvent = Emanager.getEvent(eventId);
        User currentUser = Umanager.getUser(userId);

        if (!Emanager.hasOrganizedEvent(currentUser, currentEvent)) {
            presenter.setInputResponse("This event is not organized by you. Try again.");
            return InputProcessResult.USER_DID_NOT_ORGANIZE_EVENT;
        }

        Emanager.removeEvent(eventId);
        presenter.setInputResponse("Event Canceled successfully");

        return InputProcessResult.SUCCESS;
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
