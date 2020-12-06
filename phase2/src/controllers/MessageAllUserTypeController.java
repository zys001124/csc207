package controllers;

import entities.User;
import exceptions.MessageCancelledException;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

/**
 * A controller used to handle input for sending a message to all users of specific user types (attendee, organizer, or speaker)
 */
public abstract class MessageAllUserTypeController {
    private final MessageManager messageManager;
    private final UserManager userManager;

    /**
     * Creates a MessageAllUserTypeController with the given MessageManager and UserManager
     *
     * @param messageManager - The MessageManager this object will use
     * @param userManager    - The userManager this object will use
     */
    public MessageAllUserTypeController(MessageManager messageManager, UserManager userManager) {
        this.messageManager = messageManager;
        this.userManager = userManager;
    }

    /**
     * Sends a message to all users in the system of certain user type(s), of which are specified through the checkUserType method
     *
     * @param message - The message to be sent to all specified user type(s)
     * @return an InputProcessResult - The result from the given input
     */
    public InputProcessResult sendMessage(String message) {
        try {
            if (message.equals("q")) {
                throw new MessageCancelledException(userManager.getCurrentlyLoggedIn().getUsername());
            }
            for (User user : userManager.getUsers()) {
                if (checkUserType(user)) {
//                    messageManager.sendMessage(userManager.getCurrentlyLoggedIn().getId(), user.getId(), message);
                }
            }
        } catch (MessageCancelledException e) {
            return InputProcessResult.NAVIGATE_TO_MAIN_MENU;
        }
        return InputProcessResult.SUCCESS;
    }

    /**
     * Check whether the user is an appropriate user type which should be messaged
     *
     * @param user - The user which needs to have its user type checked
     * @return a boolean representing whether the user should be messaged
     */
    protected abstract boolean checkUserType(User user);
}
