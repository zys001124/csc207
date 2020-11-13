package controllers;

import entities.User;
import exceptions.InvalidUsernameException;
import exceptions.MessageCancelledException;
import exceptions.UserNotFoundException;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

public class MessageUserController {
    private MessageManager messageManager;
    private UserManager userManager;


    public MessageUserController(MessageManager messageManager, UserManager userManager){
        this.messageManager = messageManager;
        this.userManager = userManager;
    }

    public InputProcessResult sendMessage(String username, String message) {
        try {
            if(username.equals("q") && message.equals("q")){
                throw new MessageCancelledException(userManager.getCurrentlyLoggedIn().getUsername(), username);
            }

            User.UserType userType = userManager.getCurrentlyLoggedIn().getType();
            User recipient = userManager.getUser(username);
            switch(userType){
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

    private void sendMessageAsAttendee(User recipient, String message) throws InvalidUsernameException {
        if(recipient.getType().equals(User.UserType.ORGANIZER)){
            throw new InvalidUsernameException(recipient.getUsername());
        }
        messageManager.addMessage(userManager.getCurrentlyLoggedIn().getId(), recipient.getId(), message);
    }

    private void sendMessageAsOrganizer(User recipient, String message) throws InvalidUsernameException{
        if(recipient.getType().equals(User.UserType.ORGANIZER)) {
            throw new InvalidUsernameException(recipient.getUsername());
        }
        messageManager.addMessage(userManager.getCurrentlyLoggedIn().getId(), recipient.getId(), message);
    }

    private void sendMessageAsSpeaker(User recipient, String message) throws InvalidUsernameException{
        if(recipient.getType().equals(User.UserType.ORGANIZER) ||
                !messageManager.messageSentBy(recipient.getId(), userManager.getCurrentlyLoggedIn().getId())){
            throw new InvalidUsernameException(recipient.getUsername());
        }
        messageManager.addMessage(userManager.getCurrentlyLoggedIn().getId(), recipient.getId(), message);
    }
}
