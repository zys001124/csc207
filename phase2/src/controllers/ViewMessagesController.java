package controllers;

import entities.Message;
import exceptions.UserNotFoundException;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

import java.util.Collection;

/**
 * controller class for viewing the message history between the currently logged in user
 * and the user of selection
 */
public class ViewMessagesController {

    private final MessageManager messageManager;
    private final UserManager userManager;

    /**
     * initalizer for what message Manager and UserManager for the corresponding conference
     * system
     *
     * @param messageManager the corresponding MessageManager
     * @param userManager    the corresponding UserManager
     */
    public ViewMessagesController(MessageManager messageManager, UserManager userManager) {
        this.messageManager = messageManager;
        this.userManager = userManager;
    }

    /**
     * handles the user input for what they decide to put in. Will return an InputProcessResult
     * for what their input is
     *
     * @param username the username/input for what the user put into the keyboard
     * @return An InputProcessResult which tells the program what to do next and helps to
     * navigate to the corresponding screen.
     */
    public InputProcessResult handleInput(String username) {
        if (username.equals("back")) {
            return InputProcessResult.BACK;
        }
        try {
            Collection<Message> messageBetweenTwo = messageManager.messagesBetweenTwo(userManager.getUser(username), userManager.getCurrentlyLoggedIn());
            if (messageBetweenTwo.size() == 0) {
                return InputProcessResult.NO_MESSAGE_HISTORY;
            }
            return InputProcessResult.SUCCESS;
        } catch (UserNotFoundException e) {
            return InputProcessResult.USER_NOT_FOUND;
        }
    }

}
