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
                "2. Enroll/Unenroll in event \n" +
                "3. See event schedule \n"+
                "4. Log out";
    }

    private String getSpeakerOptions() {
        return "1. Message Users in event \n" +
                "3. See event schedule \n"+
                "4. Log out";
    }

    private String getOrganizerOptions() {
        return "1. Create/cancel event \n"+
                "2. Create speaker account \n"+
                "3. Log out";
    }

}
