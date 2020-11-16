package presenters;

import controllers.InputProcessResult;
import entities.User;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

/**
 * The presenter meant to be used by the MessageUserView, for when messaging a specific user at the conference
 */
public class MessageUserPresenter extends Presenter {
    private final UserManager userManager;
    private final MessageManager messageManager;

    /**
     * Creates a MessageUserPresenter with the given UserManager
     *
     * @param messageManager - The MessageManager this object will use
     * @param userManager - The UserManager this object will use
     */
    public MessageUserPresenter(UserManager userManager, MessageManager messageManager) {
        this.userManager = userManager;
        this.messageManager = messageManager;
    }

    /**
     * Gets the String that should be printed to the console
     * before a user is given the opportunity to make an input
     *
     * @return a String - The text to be printed before a user
     * makes an input
     */
    @Override
    public String getPreInputText() {
        return "Please type in the username of the account you wish to message (type q in both fields to quit):";
    }

    /**
     * Gets a list of all possible usernames which can be messaged by the logged in user
     *
     * @return a String - A list of all usernames which can be messaged
     */
    public String getPossibleUsers() {
        User.UserType userType = userManager.getCurrentlyLoggedIn().getType();
        switch (userType) {
            case ATTENDEE: {
                return getAttendeeUserList();
            }

            case ORGANIZER: {
                return getOrganizerUserList();
            }

            case SPEAKER: {
                return getSpeakerUserList();
            }
        }
        return null;
    }

    /**
     * Gets a list of all possible usernames which can be messaged by the logged in attendee
     *
     * @return a String - A list of all usernames which can be messaged
     * Precondition: The currently logged in user is an attendee
     */
    private String getAttendeeUserList() {
        StringBuilder userList = new StringBuilder();
        for (User user : userManager.getUsers()) {
            if (user.getType() == User.UserType.ATTENDEE || user.getType() == User.UserType.SPEAKER) {
                userList.append(user.getUsername()).append("\n");
            }
        }
        return userList.toString().trim();
    }

    /**
     * Gets a list of all possible usernames which can be messaged by the logged in organizer
     *
     * @return a String - A list of all usernames which can be messaged
     * Precondition: The currently logged in user is an organizer
     */
    private String getOrganizerUserList() {
        StringBuilder userList = new StringBuilder();
        for (User user : userManager.getUsers()) {
            if (user.getType() == User.UserType.ATTENDEE || user.getType() == User.UserType.SPEAKER) {
                userList.append(user.getUsername()).append("\n");
            }
        }
        return userList.toString().trim();
    }

    /**
     * Gets a list of all possible usernames which can be messaged by the logged in speaker
     *
     * @return a String - A list of all usernames which can be messaged
     * Precondition: The currently logged in user is a speaker
     */
    private String getSpeakerUserList() {
        StringBuilder userList = new StringBuilder();
        for (User user : userManager.getUsers()) {
            if (user.getType() == User.UserType.ATTENDEE &&
                    messageManager.messageSentBy(user.getId(), userManager.getCurrentlyLoggedIn().getId())) {
                userList.append(user.getUsername()).append("\n");
            }
        }
        return userList.toString().trim();
    }

    /**
     * Gets the text that should be printed after the user has made an input
     *
     * @param result - the InputProcessResult that determines what the
     *               response to the users input should be
     * @return a String - The text that should be printed as a result of <result>
     */
    @Override
    public String getInputResponseText(InputProcessResult result) {
        if (result.equals(InputProcessResult.SUCCESS)) {
            return "Message sent successfully!";
        } else if (result.equals(InputProcessResult.USER_NOT_FOUND)) {
            return "Username not found. Please try again:";
        } else if (result.equals(InputProcessResult.INVALID_USERNAME)) {
            return "Invalid username provided. Please try again:";
        } else if (result.equals(InputProcessResult.NAVIGATE_TO_MAIN_MENU)) {
            return "Returning to main menu.";
        }
        return null;
    }

    /**
     * Gets the text that should be printed when the user is requested to input message text,
     * after having entered the username
     *
     * @return a String - The message prompt
     */
    public String messagePrompt() {
        return "Please enter your message:";
    }
}
