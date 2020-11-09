package presenters;

import entities.User;

public class MenuInputPresenter {

    public String getMenuOptions(User.UserType userType) {
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
                "3. Message User"+
                "4. Message all speakers\n"+
                "5. Message all attendees\n"+
                "5. Create speaker account \n"+
                "6. Log out";
    }

}
