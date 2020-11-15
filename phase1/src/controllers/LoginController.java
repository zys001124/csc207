package controllers;

import exceptions.IncorrectPasswordException;
import exceptions.UserNotFoundException;
import useCaseClasses.UserManager;


/**
 * A controller for the login screen
 */
public class LoginController {

    private final UserManager manager;

    /**
     * Creates a LoginController with the given UserManager
     *
     * @param manager - The UserManager this class will use to
     *                pass login information to
     */
    public LoginController(UserManager manager) {
        this.manager = manager;
    }

    /**
     * Logs the user into the system if the username and password passed
     * as input is a match for a User object in UserManager
     *
     * @param username - The username passed as input
     * @param password - The password passed as input
     * @return an InputProcessResult - The result of the input handling
     */
    public InputProcessResult verifyLogin(String username, String password) {
        try {
            manager.userLogin(username, password);
        } catch (IncorrectPasswordException e) {
            return InputProcessResult.INCORRECT_PASSWORD;
        } catch (UserNotFoundException e) {
            return InputProcessResult.USER_NOT_FOUND;
        }

        return InputProcessResult.SUCCESS;
    }

}
