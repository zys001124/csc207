package controllers;

import entities.User;
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

    public InputProcessResult setReceiver(String username) {
        User.UserType userType = userManager.getCurrentlyLoggedIn().getType();
//        try {
//
//        } catch (UserNotFoundException e) {
//            return InputProcessResult.USER_NOT_FOUND;
//        }
        return InputProcessResult.SUCCESS;
    }

    public InputProcessResult sendMessage(String message){
        return InputProcessResult.SUCCESS;
    }
}
