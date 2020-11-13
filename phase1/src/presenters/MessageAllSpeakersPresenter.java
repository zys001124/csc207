package presenters;

import controllers.InputProcessResult;

public class MessageAllSpeakersPresenter {

    public String messagePrompt() {
        return "Please enter the message you wish to be sent to all speakers (type q to quit):";
    }

    public String getMessageResult(InputProcessResult result){
        switch (result) {
            case SUCCESS: return "Messages sent successfully!";
            case NAVIGATE_TO_MAIN_MENU:; return "Returning to main menu.";
        }
        return "";
    }
}
