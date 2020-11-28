package presenters;

import controllers.InputProcessResult;
import entities.Message;
import entities.User;
import exceptions.UserNotFoundException;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

import java.util.List;

public class ViewMessagesPresenter extends Presenter{

    private final UserManager userManager;
    private final MessageManager messageManager;

    public ViewMessagesPresenter(UserManager userManager, MessageManager messageManager) {
        this.userManager = userManager;
        this.messageManager = messageManager;
    }

    public String getPreInputText(){
        return "Please type in the username you wish view your conversation history with.\n" +
                "Enter 'back' to go back to the main menu.";
    }

    public String getUsernames(){
        StringBuilder userList = new StringBuilder();
        for(User user: userManager.getUsers()){
            userList.append(user.getUsername()).append("\n");

        }
        return userList.toString().trim();
    }

    public String preMessageHistoryText(String recipientUsername) {
        return "Conversation history with " + recipientUsername + ":";
    }



    public String messageHistory(String recipientUsername) {
        try {
            List<Message> messageBetweenTwo = messageManager.messagesBetweenTwo(userManager.getUser(recipientUsername), userManager.getCurrentlyLoggedIn());
            if (messageBetweenTwo.size() == 0) {
                return "No conversation history found.";
            }
            StringBuilder messageHistory = new StringBuilder();
            for (Message message : messageBetweenTwo) {
                String messageSenderUsername = userManager.getUser(message.getSenderId()).getUsername();
                String messageRecipientUsername = userManager.getUser(message.getRecipientId()).getUsername();
                messageHistory.append("From: ").append(messageSenderUsername).append(", To: ").append(messageRecipientUsername).append(", Time: ").append(message.getTimeSent().toString()).append(", Message: \n").append(message.getMessageText()).append("\n");
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
