package presenters;

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

    public String getInputResponse() {
        return inputResponse;
    }

}
