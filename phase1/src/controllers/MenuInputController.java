package controllers;

import UI.ConsoleView;
import entities.User;
import exceptions.InvalidInputException;
import useCaseClasses.UserManager;
import useCaseClasses.MessageManager;

public class MenuInputController {

    private UserManager userManager;

    public MenuInputController(UserManager um) {
        userManager = um;
    }

    public ConsoleView.ConsoleViewType getNextScreen(String input) throws InvalidInputException {
        User.UserType userType = userManager.getCurrentlyLoggedIn().getType();

        return null; //TODO finish method
    }
}
