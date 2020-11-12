package presenters;

import controllers.InputProcessResult;

public class MessageUserPresenter {

    private String inputResponse = "";

    public String getIntro(){
        return "Please type in the username of the account you wish to message:";
    }

    public String getPossibleUsers(){
        return "";
    }

    public String messagePrompt(){
        return "Please enter your message:";
    }

    public void setInputResponse(String message) {
        inputResponse = message;
    }

    public String getInputResponse(InputProcessResult result) {
        if (result == InputProcessResult.SUCCESS){
            return "Message sent!";
        }else if(result == InputProcessResult.USER_NOT_FOUND){
            return "Speaker added successfully";
        }else{return null;}
    }
}
