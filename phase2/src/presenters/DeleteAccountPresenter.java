package presenters;

import controllers.InputProcessResult;
import entities.User;
import useCaseClasses.UserManager;

/**
 * A presenter for generating strings that should be displayed
 * on the console when an organizer wants to cancel an existing event
 */

public class DeleteAccountPresenter extends Presenter{

    public final UserManager userManager;

    /**
     * Creates an DeleteAccountPresenter with the given UserManager
     *
     * @param um the UserManager this controller will use
     */

    public DeleteAccountPresenter(UserManager um){
        userManager = um;
    }


    /**
     * Generate the String that should be displayed before the users type in their input
     *
     * @return the String that should be displayed before the input is made
     */

    @Override
    public String getPreInputText() {
        return "Here is the list of users.\n" +
                "To delete an user account please type in the name of the user account you wish to delete \n" +
                "Type \"back\" to return to the menu";
    }

    /**
     * Generate the String that contains the list of names of all the
     * users in the given UserManager
     *
     * @return the String that contains the list of names of all the
     * users in the given UserManager
     */

    public String getAllUsers() {
        String users = "";
        for (User u : userManager.getUsers()) {
            users = users + u.getUsername() + '\n';
        }
        return users;
    }

    /**
     * Generate the String that should be displayed after the input is made
     *
     * @param result - the InputProcessResult that determines what the
     *               response to the users input should be
     * @return the String that should be displayed after the input is made
     */

    @Override
    public String getInputResponseText(InputProcessResult result) {
        if (result == InputProcessResult.BACK) {
            return "";
        } else if (result == InputProcessResult.USER_NOT_FOUND) {
            return "This user does not exist. Try again.\n";
        } else if (result == InputProcessResult.INVALID_USER_TYPE) {
            return "This user can not be deleted. Try again.\n";
        } else {
            return "Account deleted successfully";
        }
    }
}
