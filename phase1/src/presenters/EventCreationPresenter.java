package presenters;

import controllers.InputProcessResult;

public class EventCreationPresenter {

    private String inputResponse = "";

    public String getIntro() {
        return "The event should be between 9am-5pm in November 10. There are 6 possible rooms for speeches. " +
                "The room number is from 0-5. Every speech is one hour long\n"+
                "To add an event, enter information in following way: " +
                "title,time,speakerUsername,roomNumber \n"+
                "Time should be written in this form yyyy-MM-dd HH:mm\n"+
                "Type \"back\" to return to the menu";
    }

    public void setInputResponse(String message) {
        inputResponse = message;
    }

    public String getInputResponse(InputProcessResult result) {
        if (result == InputProcessResult.BACK){
            return null;
        }else if(result == InputProcessResult.USER_NOT_FOUND){
            return "The speaker user is not a register user";
        }else if (result == InputProcessResult.USER_NOT_SPEAKER){
            return "The given speaker is not a speaker";
        }else if (result == InputProcessResult.TIMESLOT_FULL){
            return "This time slot is full";
        }else if (result == InputProcessResult.SUCCESS){
            return "The event is added";
        }else{return "The room is already booked. Try another room";}

    }

}
