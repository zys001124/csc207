package presenters;

public class MessageAllAttendingEventPresenter {

    private String inputResponse = "";

    public String getIntro(){
        return "Please enter the UUID of the event you would like to message all of the attendees for:\n" +
                "Enter \"back\" to go back to the main menu.";
    }

    public String getMessage(){
        return "Please enter the message you wish to send out to your users:";
    }


    public void setInputResponse(String s){ inputResponse = s;}

    public String getInputResponse(){ return inputResponse;}
}
