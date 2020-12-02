package presenters;

import controllers.InputProcessResult;

public class ChangeEventCapacityPresenter extends Presenter{

    private final String inputResponse = "";

    public String getPreInputText() {
        return "Please state the start time of the event and room number and the new event capacity you want\n" +
                "The room capacity is 60. please write your request in this form:\n"+
                "Time should be written in this form yyyy-MM-dd HH:mm\n" +
                "startTime,roomNum,newEventCapacity";
    }


    public String getInputResponseText(InputProcessResult result) {
        if (result == InputProcessResult.SUCCESS) {
            return "Capacity change successful";
        } else if (result == InputProcessResult.BACK) {
            return "";
        } else if(result == InputProcessResult.EVENT_DOES_NOT_EXIST){
            return "event does not exist. Try again";
        } else {
            return "new Event is over room capacity please give different event capacity";
        }
    }
}
