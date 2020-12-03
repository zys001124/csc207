package presenters;

import controllers.InputProcessResult;
import entities.Message;
import entities.User;
import exceptions.UserNotFoundException;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

import java.util.Collection;
import java.util.List;

/**
 * Presenter for option to view the message history between the currently looged in user
 * and the user they wish to view their convo history with
 */
public class ViewMessagesPresenter extends Presenter{

    private final UserManager userManager;
    private final MessageManager messageManager;

    /**
     * initalizer for the presenter that allows to view convo history
     * @param userManager - user manager for the corresponding part of the program
     * @param messageManager - message manager for teh corresponding part of the program
     */
    public ViewMessagesPresenter(UserManager userManager, MessageManager messageManager) {
        this.userManager = userManager;
        this.messageManager = messageManager;
    }

    /**
     * the string for the first part of the menu option
     * @return string that greets the user for this option
     */
    public String getPreInputText(){
        return "Please type in the username you wish view your conversation history with.\n" +
                "Enter 'back' to go back to the main menu.";
    }

    /**
     * String of usernames for the user to view the message history with.
     * @return a string of all possible usernames
     */
    public String getUsernames(){
        StringBuilder userList = new StringBuilder();
        for(User user: userManager.getUsers()){
            userList.append(user.getUsername()).append("\n");

        }
        return userList.toString().trim();
    }

    /**
     * pretext for the message history
     * @param recipientUsername the username that the user is veiwing their message history
     *                          with
     * @return returns the pre message string
     */
    public String preMessageHistoryText(String recipientUsername) {
        return "Conversation history with " + recipientUsername + ":";
    }


    /**
     * the full message history between the currently logged in user and the user they
     * wish to view
     * @param recipientUsername the user that the currently logged in user is viewing
     *                          their message with
     * @return the full message history as a string
     */
    public String messageHistory(String recipientUsername) {
        try {
            Collection<Message> messageBetweenTwo = messageManager.messagesBetweenTwo(userManager.getUser(recipientUsername), userManager.getCurrentlyLoggedIn());
            if (messageBetweenTwo.size() == 0) {
                return "No conversation history found.";
            }
            StringBuilder messageHistory = new StringBuilder();
            for (Message message : messageBetweenTwo) {
                String messageSenderUsername = userManager.getUser(message.getSenderId()).getUsername();
                String messageRecipientUsername = userManager.getUser(message.getRecipientId()).getUsername();
                messageHistory.append("From: ").append(messageSenderUsername).append(", To: ").append(messageRecipientUsername).append(", Time: ").append(message.getTimeSent().toString()).append(", Message: ").append(message.getMessageText()).append("\n");
            }
            return messageHistory.toString().trim();
        } catch (UserNotFoundException e) {
            return "User Not found.";
        }
    }


    @Override
    public String getInputResponseText(InputProcessResult result) {
        if(result == InputProcessResult.BACK){
            return "Navigating back to Main Menu.";
        }
        else if(result == InputProcessResult.USER_NOT_FOUND){
            return "Could not find user. Please Try again.";
        }
        else{
            return "Enter anything to continue to Main Menu.";
        }
    }
}
