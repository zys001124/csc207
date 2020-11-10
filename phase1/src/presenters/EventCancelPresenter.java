package presenters;


public class EventCancelPresenter {

    private String inputResponse = "";

    public String getIntro() {
        return "To cancel an event type in the following: [userId] [eventId] \n"+
                "Type \"back\" to return to the menu";
    }

    public void setInputResponse(String message) {
        inputResponse = message;
    }

    public String getInputResponse() {
        return inputResponse;
    }
}
