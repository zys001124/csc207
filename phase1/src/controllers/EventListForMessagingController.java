package controllers;

import UI.ConsoleView;
import entities.User;
import exceptions.UsernameAlreadyExistsException;
import presenters.EventListForMessagingPresenter;
import useCaseClasses.EventManager;

public class EventListForMessagingController {

    private EventManager eventManager;
    private EventListForMessagingPresenter presenter;




    public ConsoleView.ConsoleViewType getNextScreen(String input) {

        if(input.equals("back")) {
            return ConsoleView.ConsoleViewType.MAIN_MENU;
        }
        presenter.setInputResponse("Could not understand your input. Please try again.");
        return ConsoleView.ConsoleViewType.EVENT_LIST_FOR_MESSAGING;

    }
}
