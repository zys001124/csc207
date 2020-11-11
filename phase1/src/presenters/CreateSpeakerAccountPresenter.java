package presenters;

import controllers.InputProcessResult;

public class CreateSpeakerAccountPresenter {

    private String inputResponse = "";

    public String getIntro() {
        return "To create a speaker account type in the following: [username] [password] \n"+
                "Type \"back\" to return to the menu";
    }

    public void setInputResponse(String message) {
        inputResponse = message;
    }

    public String getInputResponse(InputProcessResult result) {
        if (result == InputProcessResult.INVALID_INPUT){
            return "Expecting two, and only two inputs. Try again.";
        }else if(result == InputProcessResult.SUCCESS){
            return "Speaker added successfully";
        }else if (result == InputProcessResult.USERNAME_TAKEN){
            return "Username taken. Try again.";
        }else{return null;}
    }

}
