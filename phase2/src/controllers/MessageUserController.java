package controllers;

import entities.User;
import exceptions.InvalidUsernameException;
import exceptions.MessageCancelledException;
import exceptions.UserNotFoundException;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

/**
 * A controller used to handle input for sending a message to a specific user
 */
public class MessageUserController {
    private final MessageManager messageManager;
    private final UserManager userManager;

    /**
     * Creates a MessageUserController with the given MessageManager and UserManager
     *
     * @param messageManager - The MessageManager this object will use
     * @param userManager    - The userManager this object will use
     */
    public MessageUserController(MessageManager messageManager, UserManager userManager) {
        this.messageManager = messageManager;
        this.userManager = userManager;
    }

    /**
     * Sends a message to a specific user
     *
     * @param username - The username of the recipient
     * @param message  - The message text to be sent
     * @return an InputProcessResult - The result from the given input
     */
    public InputProcessResult sendMessage(String username, String message) {
        try {
            if (message.equals("back")) {
                throw new MessageCancelledException(userManager.getCurrentlyLoggedIn().getUsername());
            }

            User.UserType userType = userManager.getCurrentlyLoggedIn().getType();
            User recipient = userManager.getUser(username);
            switch (userType) {
                case ATTENDEE: {
                    sendMessageAsAttendee(recipient, message);
                    break;
                }
                case ORGANIZER: {
                    sendMessageAsOrganizer(recipient, message);
                    break;
                }
                case SPEAKER: {
                    sendMessageAsSpeaker(recipient, message);
                }
            }
        } catch (UserNotFoundException e) {
            return InputProcessResult.USER_NOT_FOUND;
        } catch (InvalidUsernameException e) {
            return InputProcessResult.INVALID_USERNAME;
        } catch (MessageCancelledException e) {
            return InputProcessResult.NAVIGATE_TO_MAIN_MENU;
        }
        return InputProcessResult.SUCCESS;
    }

    /**
     * Sends a message to a specific user while currently logged in as an attendee. Assures that the logged in
     * attendee can only message other attendees and speakers, otherwise throws an InvalidUsernameException exception
     *
     * @param message - The message to be sent to all specified user type(s)
     *                Precondition: Currently logged in user is an Attendee
     */
    private void sendMessageAsAttendee(User recipient, String message) throws InvalidUsernameException {
        if (recipient.getType().equals(User.UserType.ORGANIZER)) {
            throw new InvalidUsernameException(recipient.getUsername());
        }
//        messageManager.sendMessage(userManager.getCurrentlyLoggedIn().getId(), recipient.getId(), message);
    }

    /**
     * Sends a message to a specific user while currently logged in as an organizer. Assures that the logged in
     * organizer can only message other attendees and speakers, otherwise throws an InvalidUsernameException exception
     *
     * @param message - The message to be sent to all specified user type(s)
     *                Precondition: Currently logged in user is an Organizer
     */
    private void sendMessageAsOrganizer(User recipient, String message) throws InvalidUsernameException {
        if (recipient.getType().equals(User.UserType.ORGANIZER)) {
            throw new InvalidUsernameException(recipient.getUsername());
        }
//        messageManager.sendMessage(userManager.getCurrentlyLoggedIn().getId(), recipient.getId(), message);
    }

    /**
     * Sends a message to a specific user while currently logged in as a speaker. Assures that the logged in
     * speaker can only reply to attendees who have previously sent them messages, otherwise throws an InvalidUsernameException exception
     *
     * @param message - The message to be sent to all specified user type(s)
     *                Precondition: Currently logged in user is a Speaker
     */
    private void sendMessageAsSpeaker(User recipient, String message) throws InvalidUsernameException {
        if (recipient.getType().equals(User.UserType.ORGANIZER) ||
                recipient.getType().equals(User.UserType.SPEAKER) ||
                !messageManager.messageSentBy(recipient.getId(), userManager.getCurrentlyLoggedIn().getId())) {
            throw new InvalidUsernameException(recipient.getUsername());
        }
//        messageManager.sendMessage(userManager.getCurrentlyLoggedIn().getId(), recipient.getId(), message);
    }
}
