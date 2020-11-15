package presenters;

import controllers.InputProcessResult;
import entities.User;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

public class MessageUserPresenter extends Presenter {
    private UserManager userManager;
    private MessageManager messageManager;

    public MessageUserPresenter(UserManager userManager, MessageManager messageManager){
        this.userManager = userManager;
        this.messageManager = messageManager;
    }

    public String getPreInputText(){
        return "Please type in the username of the account you wish to message (type q in both fields to quit):";
    }

    public String getPossibleUsers(){
        User.UserType userType = userManager.getCurrentlyLoggedIn().getType();
        switch(userType){
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

    private String getAttendeeUserList(){
        StringBuilder userList = new StringBuilder();
        for(User user : userManager.getUsers()) {
            if(user.getType() == User.UserType.ATTENDEE || user.getType() == User.UserType.SPEAKER) {
                userList.append(user.getUsername()).append("\n");
            }
        }
        return userList.toString().trim();
    }

    private String getOrganizerUserList(){
        StringBuilder userList = new StringBuilder();
        for(User user : userManager.getUsers()) {
            if(user.getType() == User.UserType.ATTENDEE || user.getType() == User.UserType.SPEAKER) {
                userList.append(user.getUsername()).append("\n");
            }
        }
        return userList.toString().trim();
    }

    private String getSpeakerUserList(){
        StringBuilder userList = new StringBuilder();
        for(User user : userManager.getUsers()) {
            if(user.getType() == User.UserType.ATTENDEE &&
                    messageManager.messageSentBy(user.getId(), userManager.getCurrentlyLoggedIn().getId())) {
                userList.append(user.getUsername()).append("\n");
            }
        }
        return userList.toString().trim();
    }

    public String getInputResponseText(InputProcessResult result) {
        if(result.equals(InputProcessResult.SUCCESS)) {
            return "Message sent successfully!";
        } else if(result.equals(InputProcessResult.USER_NOT_FOUND)) {
            return "Username not found. Please try again:";
        } else if(result.equals(InputProcessResult.INVALID_USERNAME)) {
            return "Invalid username provided. Please try again:";
        } else if(result.equals(InputProcessResult.NAVIGATE_TO_MAIN_MENU)) {
            return "Returning to main menu.";
        }
        return null;
    }

    public String messagePrompt(){
        return "Please enter your message:";
    }
}
