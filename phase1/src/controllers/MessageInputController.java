package controllers;

import useCaseClasses.UserManager;
import useCaseClasses.MessageManager;

import java.util.UUID;

public class MessageInputController {

    private UserManager userManager;
    private MessageManager messageManager;

    public MessageInputController(UserManager am, MessageManager mm) {
        userManager = am;
        messageManager = mm;
    }

    public void sendMessage(UUID receiver, String message)
    {
        messageManager.addMessage(userManager.getCurrentlyLoggedIn().getId(),
                receiver, message);
    }
}
