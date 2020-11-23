package controllers;

import entities.User;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

/**
 * A controller used to handle input for sending a message all speakers
 */
public class MessageAllSpeakersController extends MessageAllUserTypeController {

    /**
     * Creates a MessageAllSpeakersController with the given MessageManager and UserManager
     *
     * @param messageManager - The MessageManager this object will use
     * @param userManager    - The userManager this object will use
     */
    public MessageAllSpeakersController(MessageManager messageManager, UserManager userManager) {
        super(messageManager, userManager);
    }

    /**
     * Check whether the user is a speaker and thus whether it should be messaged
     *
     * @param user - The user which needs to have its user type checked
     * @return a boolean representing whether the user is a speaker
     */
    protected boolean checkUserType(User user) {
        return user.getType().equals(User.UserType.SPEAKER);
    }
}
