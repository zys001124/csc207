package controllers;

import entities.User;
import exceptions.MessageCancelledException;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

public class MessageAllSpeakersController {
    private final MessageManager messageManager;
    private final UserManager userManager;

    public MessageAllSpeakersController(MessageManager messageManager, UserManager userManager) {
        this.messageManager = messageManager;
        this.userManager = userManager;
    }

    public InputProcessResult sendMessage(String message) {
        try {
            if (message.equals("q")) {
                throw new MessageCancelledException(userManager.getCurrentlyLoggedIn().getUsername(), "all speakers");
            }
            for (User user : userManager.getUsers()) {
                if (user.getType().equals(User.UserType.SPEAKER)) {
                    messageManager.addMessage(userManager.getCurrentlyLoggedIn().getId(), user.getId(), message);
                }
            }
        } catch (MessageCancelledException e) {
            return InputProcessResult.NAVIGATE_TO_MAIN_MENU;
        }
        return InputProcessResult.SUCCESS;
    }
}
