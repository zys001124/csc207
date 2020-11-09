package presenters;

public class CreateSpeakerAccountPresenter {

    private String inputResponse = "";

    public String getIntro() {
        return "To create a speaker account type in the following: [username] [password] \n"+
                "Type \"back\" to return to the menu";
    }

    public void setInputResponse(String message) {
        inputResponse = message;
    }

    public String getInputResponse() {
        return inputResponse;
    }

}
