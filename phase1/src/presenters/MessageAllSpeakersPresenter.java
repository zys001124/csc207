package presenters;

import controllers.InputProcessResult;

public class MessageAllSpeakersPresenter extends Presenter {

    public String getPreInputText() {
        return "Please enter the message you wish to be sent to all speakers (type q to quit):";
    }

    public String getInputResponseText(InputProcessResult result){
        switch (result) {
            case SUCCESS: return "Messages sent successfully!";
            case NAVIGATE_TO_MAIN_MENU:; return "Returning to main menu.";
        }
        return "";
    }
}
