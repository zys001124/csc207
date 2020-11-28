package controllers;

import entities.Message;
import exceptions.UserNotFoundException;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

import java.util.List;

public class ViewMessagesController {

    private final MessageManager messageManager;
    private final UserManager userManager;

    public ViewMessagesController(MessageManager messageManager, UserManager userManager){
        this.messageManager = messageManager;
        this.userManager = userManager;
    }

    public InputProcessResult handleInput(String username) {
        if(username.equals("back")){
            return InputProcessResult.BACK;
        }
        try {
            List<Message> messageBetweenTwo = messageManager.messagesBetweenTwo(userManager.getUser(username), userManager.getCurrentlyLoggedIn());
            if(messageBetweenTwo.size() == 0){
                return InputProcessResult.NO_MESSAGE_HISTORY;
            }
            return InputProcessResult.SUCCESS;
        }
        catch (UserNotFoundException e) {
            return InputProcessResult.USER_NOT_FOUND;
        }
    }

}
