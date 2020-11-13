package presenters;

import controllers.InputProcessResult;

public class CreateSpeakerAccountPresenter extends Presenter {
    
    @Override
    public String getPreInputText() {
        return "To create a speaker account type in the following: [username] [password] \n"+
                "Type \"back\" to return to the menu";
    }

    @Override
    public String getInputResponseText(InputProcessResult result) {
        if (result == InputProcessResult.INVALID_INPUT){
            return "Expecting two, and only two inputs. Try again.";
        }else if(result == InputProcessResult.SUCCESS){
            return "Speaker added successfully";
        }else if (result == InputProcessResult.USERNAME_TAKEN){
            return "Username taken. Try again.";
        }else{return null;}
    }
}
