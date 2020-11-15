package controllers;

import entities.User;
import exceptions.UsernameAlreadyExistsException;
import useCaseClasses.UserManager;

/**
 * A controller for handling input when an Organizer is
 * creating a Speaker account
 */
public class CreateSpeakerAccountController {

    private final UserManager userManager;

    /**
     * Creates a CreateSpeakerAccountController with the given UserManager
     *
     * @param um - The userManager this controller will use
     */
    public CreateSpeakerAccountController(UserManager um) {
        userManager = um;
    }

    /**
     * Handles the input given by the user
     *
     * @param input the users input
     * @return an InputProcessResult enum that details what happened
     * as a result of the given input
     */
    public InputProcessResult getNextScreen(String input) {

        if (input.equals("back")) {
            return InputProcessResult.BACK;
        }

        String[] usernameAndPassword = input.split(" ");

        // Invalid input
        if (usernameAndPassword.length != 2) {
            return InputProcessResult.INVALID_INPUT;
        }

        String username = usernameAndPassword[0];
        String password = usernameAndPassword[1];

        try {
            userManager.addUser(User.UserType.SPEAKER, username, password);
            return InputProcessResult.SUCCESS;
        } catch (UsernameAlreadyExistsException e) {
            return InputProcessResult.USERNAME_TAKEN;
        }

    }

}
