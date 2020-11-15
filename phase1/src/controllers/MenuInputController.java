package controllers;

import entities.User;
import useCaseClasses.UserManager;

/**
 * A controller meant to be used to handle input from MenuInputView (main menu)
 */
public class MenuInputController {

    private final UserManager userManager;

    /**
     * Creates a MenuInputController with the given UserManager
     *
     * @param um - The UserManager this object will use
     */
    public MenuInputController(UserManager um) {
        userManager = um;
    }

    /**
     * Handles input from the MenuInputView (main menu)
     *
     * @param input - the users input from the main menu
     * @return an InputProcessResult - the result of the input handling
     */
    public InputProcessResult handleInput(String input) {
        User.UserType userType = userManager.getCurrentlyLoggedIn().getType();

        int parsedInput;
        try {
            parsedInput = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return InputProcessResult.INVALID_INPUT;
        }

        switch (userType) {
            case ORGANIZER:
                return parseOrganizerInput(parsedInput);
            case SPEAKER:
                return parseSpeakerInput(parsedInput);
            default:
                return parseAttendeeInput(parsedInput);
        }
    }

    private InputProcessResult parseAttendeeInput(int input) {
        switch (input) {
            case 1:
                return InputProcessResult.NAVIGATE_TO_MESSAGE_USER;
            case 2:
                return InputProcessResult.NAVIGATE_TO_ENROLL_IN_EVENT;
            case 3:
                return InputProcessResult.NAVIGATE_TO_UNENROLL_IN_EVENT;
            case 4:
                return InputProcessResult.NAVIGATE_TO_EVENT_SCHEDULE;
            case 5:
                return null;
            default:
                return InputProcessResult.INVALID_INPUT;
        }
    }

    private InputProcessResult parseSpeakerInput(int input) {
        switch (input) {
            case 1:
                return InputProcessResult.NAVIGATE_TO_MESSAGE_ALL_ATTENDING_EVENT;
            case 2:
                return InputProcessResult.NAVIGATE_TO_MESSAGE_USER;
            case 3:
                return InputProcessResult.NAVIGATE_TO_EVENT_SCHEDULE;
            case 4:
                return null;
            default:
                return InputProcessResult.INVALID_INPUT;
        }
    }


    private InputProcessResult parseOrganizerInput(int input) {
        switch (input) {
            case 1:
                return InputProcessResult.NAVIGATE_TO_CREATE_EVENT;
            case 2:
                return InputProcessResult.NAVIGATE_TO_CANCEL_EVENT;
            case 3:
                return InputProcessResult.NAVIGATE_TO_MESSAGE_USER;
            case 4:
                return InputProcessResult.NAVIGATE_TO_MESSAGE_ALL_SPEAKERS;
            case 5:
                return InputProcessResult.NAVIGATE_TO_MESSAGE_ALL_ATTENDEES;
            case 6:
                return InputProcessResult.NAVIGATE_TO_CREATE_SPEAKER_ACCOUNT;
            case 7:
                return null;
            default:
                return InputProcessResult.INVALID_INPUT;
        }
    }

}
