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

        String eventName = input;

        if (!presenter.getAllEvents().contains(eventName)) {
            presenter.setInputResponse("This event does not exist. Try again.\n");
            return InputProcessResult.EVENT_DOES_NOT_EXIST;
        }

          Event currentEvent = Emanager.getEvent(eventName);
          User currentUser = Umanager.getCurrentlyLoggedIn();

        if (!Emanager.hasOrganizedEvent(currentUser,currentEvent)) {
            presenter.setInputResponse("This event is not organized by you. Try again.\n");
            return InputProcessResult.USER_DID_NOT_ORGANIZE_EVENT;
        }

        Emanager.removeEvent(currentEvent.getId());
        presenter.setInputResponse("Event Canceled successfully");

        return InputProcessResult.SUCCESS;
    }

}
