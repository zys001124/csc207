package presenters;

import controllers.InputProcessResult;

public class MessageAllAttendingEventPresenter {

    //private String inputResponse = "";

    public String getIntro(){
        return "Please enter the event name of the event you would like to message all of the attendees for:\n" +
                "Enter \"back\" to go back to the main menu.";
    }

    public String getMessage(){
        return "Please enter the message you wish to send out to your users:";
    }


    //public void setInputResponse(String s){ inputResponse = s;}

    public String getInputResponse(InputProcessResult result){
        if(result == InputProcessResult.NAVIGATE_TO_EVENT_LIST_FOR_MESSAGING) {
            return "Could Not understand your input. Staying on this screen";
        }
        else {
            return "Returning to Main Menu";
        }
    }
}
