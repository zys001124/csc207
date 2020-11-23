package controllers;

import entities.User;
import exceptions.UserTypeDoesNotExistException;
import exceptions.UsernameAlreadyExistsException;
import useCaseClasses.UserManager;

/**
 * A controller for handling input when an Organizer is
 * creating a Speaker account
 */
public class CreateAccountController {

    private final UserManager userManager;

    /**
     * Creates a CreateAccountController with the given UserManager
     *
     * @param um - The userManager this controller will use
     */
    public CreateAccountController(UserManager um) {
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

        String[] usernamePasswordAndType = input.split(" ");

        // Invalid input
        if (usernamePasswordAndType.length != 3) {
            return InputProcessResult.INVALID_INPUT;
        }

        String username = usernamePasswordAndType[0];
        String password = usernamePasswordAndType[1];

        try {
            User.UserType type = userManager.parseType(usernamePasswordAndType[2]);
            userManager.addUser(type, username, password);
            return InputProcessResult.SUCCESS;
        } catch (UsernameAlreadyExistsException e) {
            return InputProcessResult.USERNAME_TAKEN;
        } catch (UserTypeDoesNotExistException e) {
            return InputProcessResult.INVALID_USER_TYPE;
        }

    }

}
