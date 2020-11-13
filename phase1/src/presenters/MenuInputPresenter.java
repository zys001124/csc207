package presenters;

import controllers.InputProcessResult;
import entities.User;
import useCaseClasses.UserManager;

public class MenuInputPresenter extends Presenter {


    private UserManager userManager;
    public MenuInputPresenter(UserManager manager) {
        userManager = manager;
    }

    public String getPreInputText() {
        User.UserType userType = userManager.getCurrentlyLoggedIn().getType();
        if(userType == User.UserType.ATTENDEE) {
            return getAttendeeOptions();
        }
        else if(userType == User.UserType.SPEAKER) {
            return getSpeakerOptions();
        }
        else{
            return getOrganizerOptions();
        }
    }

    private String getAttendeeOptions() {
        return "1. Message User \n" +
                "2. Enroll in an event \n" +
                "3. Unenroll in an event \n"+
                "4. See event schedule \n"+
                "5. Log out";
    }

    private String getSpeakerOptions() {
        return "1. Message all Attendees of your event \n" +
                "2. Message Attendee \n"+
                "3. See event schedule \n"+
                "4. Log out";
    }

    private String getOrganizerOptions() {
        return "1. Create event \n"+
                "2. Cancel event \n"+
                "3. Message User \n"+
                "4. Message all speakers\n"+
                "5. Message all attendees\n"+
                "6. Create speaker account \n"+
                "7. Log out";
    }

    public String getInputResponseText(InputProcessResult result)
    {
        if(result == InputProcessResult.INVALID_INPUT)
        {
            return "Invalid input, please try again.";
        }
        else {
            return "";
        }
    }

}
