package presenters;

import controllers.InputProcessResult;

/**
 * A presenter meant to be used for generating Strings to be printed
 * to the console when an Organizer is making a Speaker account
 */
public class CreateAccountPresenter extends Presenter {

    /**
     * Gets the String that should be printed before a user makes an input
     *
     * @return the String that should be printed before an input is made
     */
    @Override
    public String getPreInputText() {
        return "To create an account, type in the following: [username] [password] [type]\n" +
                "Valid user types are: ATTENDEE, ORGANIZER, SPEAKER, VIP, ADMIN \n" +
                "Type \"back\" to return to the menu";
    }

    /**
     * Gets the String that should be printed after the user has
     * made an input
     *
     * @param result - the InputProcessResult that determines what the
     *               response to the users input should be
     * @return the String that should be printed after the user has
     * made an input
     */
    @Override
    public String getInputResponseText(InputProcessResult result) {
        if (result == InputProcessResult.INVALID_INPUT) {
            return "Expecting three, and only three inputs. Try again.";
        } else if (result == InputProcessResult.SUCCESS) {
            return "Account added successfully";
        } else if (result == InputProcessResult.USERNAME_TAKEN) {
            return "Username taken. Try again.";
        } else if (result == InputProcessResult.INVALID_USER_TYPE) {
            return "Invalid user type given. Try again.";
        } else if (result == InputProcessResult.UNQUALIFIED_USER) {
            return "You are not qualified to create this type of user. Try again.";
        } else {
            return "";
        }
    }
}
