package controllers;

import entities.User;
import exceptions.MessageCancelledException;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

public class MessageAllAttendeesController {
    private MessageManager messageManager;
    private UserManager userManager;

    public MessageAllAttendeesController(MessageManager messageManager, UserManager userManager){
        this.messageManager = messageManager;
        this.userManager = userManager;
    }

    public InputProcessResult sendMessage(String message) {
        try {
            if(message.equals("q")){
                throw new MessageCancelledException(userManager.getCurrentlyLoggedIn().getUsername(), "all attendees");
            }
            for(User user : userManager.getusers()){
                if(user.getType().equals(User.UserType.ATTENDEE)){
                    messageManager.addMessage(userManager.getCurrentlyLoggedIn().getId(), user.getId(), message);
                }
            }
        } catch (MessageCancelledException e){
            return InputProcessResult.NAVIGATE_TO_MAIN_MENU;
        } return InputProcessResult.SUCCESS;
    }
}
