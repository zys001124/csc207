package presenters;

import controllers.InputProcessResult;

public class MessageAllAttendeesPresenter extends Presenter {

    public String getPreInputText() {
        return "Please enter the message you wish to be sent to all attendees (type q to quit):";
    }

    public String getInputResponseText(InputProcessResult result) {
        switch (result) {
            case SUCCESS:
                return "Messages sent successfully!";
            case NAVIGATE_TO_MAIN_MENU:
                return "Returning to main menu.";
        }
        return "";
    }
}