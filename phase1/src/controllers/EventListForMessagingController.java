package controllers;

import UI.ConsoleView;
import entities.User;
import exceptions.UsernameAlreadyExistsException;
import presenters.EventListForMessagingPresenter;
import useCaseClasses.EventManager;

public class EventListForMessagingController {

    private EventManager eventManager;
    private EventListForMessagingPresenter presenter;




    public InputProcessResult getNextScreen(String input) {

        if(input.equals("back")) {
            return InputProcessResult.BACK;
        }
        //presenter.setInputResponse("Could not understand your input. Please try again.");
        return InputProcessResult.NAVIGATE_TO_EVENT_LIST_FOR_MESSAGING;

    }
}
