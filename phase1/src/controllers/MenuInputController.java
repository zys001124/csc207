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

    public ConsoleView.ConsoleViewType getNextScreen(String input) {
        User.UserType userType = userManager.getCurrentlyLoggedIn().getType();

        int parsedInput;
        try {
            parsedInput = Integer.parseInt(input);
        }
        catch (NumberFormatException e) {
            return ConsoleView.ConsoleViewType.MAIN_MENU;
        }

        switch (userType) {
            case ORGANIZER: return parseOrganizerInput(parsedInput);
            case SPEAKER: return parseSpeakerInput(parsedInput);
            default: return parseAttendeeInput(parsedInput);
        }
    }

    private ConsoleView.ConsoleViewType parseAttendeeInput(int input) {
        switch (input) {
            case 1: return ConsoleView.ConsoleViewType.MESSAGE_USERS_MENU;
            case 2: return ConsoleView.ConsoleViewType.ENROLL_IN_EVENT;
            case 3: return ConsoleView.ConsoleViewType.UNENROLL_IN_EVENT;
            case 4: return ConsoleView.ConsoleViewType.EVENT_SCHEDULE;
            case 5: return null;
            default: return ConsoleView.ConsoleViewType.MAIN_MENU;
        }
    }

    private ConsoleView.ConsoleViewType parseSpeakerInput(int input) {
        switch (input) {
            case 1: return ConsoleView.ConsoleViewType.EVENT_LIST_FOR_MESSAGING;
            case 2: return ConsoleView.ConsoleViewType.MESSAGE_USER;
            case 3: return ConsoleView.ConsoleViewType.EVENT_SCHEDULE;
            case 4: return null;
            default: return ConsoleView.ConsoleViewType.MAIN_MENU;
        }
    }


    private ConsoleView.ConsoleViewType parseOrganizerInput(int input) {
        switch (input) {
            case 1: return ConsoleView.ConsoleViewType.CREATE_EVENT;
            case 2: return ConsoleView.ConsoleViewType.CANCEL_EVENT;
            case 3: return ConsoleView.ConsoleViewType.MESSAGE_USER;
            case 4: return ConsoleView.ConsoleViewType.MESSAGE_ALL_SPEAKERS;
            case 5: return ConsoleView.ConsoleViewType.MESSAGE_ALL_ATTENDEES;
            case 6: return ConsoleView.ConsoleViewType.CREATE_SPEAKER_ACCOUNT;
            case 7: return null;
            default: return ConsoleView.ConsoleViewType.MAIN_MENU;
        }
    }
}
