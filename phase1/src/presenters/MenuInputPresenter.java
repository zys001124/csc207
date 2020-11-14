package presenters;

import controllers.InputProcessResult;
import entities.User;
import useCaseClasses.UserManager;

/**
 * The presenter meant to be used by the MenuInputView (main menu)
 */
public class MenuInputPresenter extends Presenter {

    private UserManager userManager;

    /**
     * Creates a MenuInputPresenter with the given UserManager
     * @param manager - The UserManager this class will use
     */
    public MenuInputPresenter(UserManager manager) {
        userManager = manager;
    }

    /**
     * Gets the text that should be printed before the user makes
     * an input
     *
     * @return a String - the text that should be printed before
     * the user makes an input
     */
    @Override
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

    /**
     * Gets the text that should be printed after the user has made an input
     * @param result - the InputProcessResult that determines what the
     *               response to the users input should be
     * @return a String - The text that should be printed as a result of <result>
     */
    @Override
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
}
