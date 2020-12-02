package presenters;

import controllers.InputProcessResult;
import entities.User;
import useCaseClasses.UserManager;

public class DeleteAccountPresenter extends Presenter{

    public final UserManager userManager;

    public DeleteAccountPresenter(UserManager um){
        userManager = um;
    }

    @Override
    public String getPreInputText() {
        return "Here is the list of users.\n" +
                "To delete an user account please type in the name of the user account you wish to delete \n" +
                "Type \"back\" to return to the menu";
    }

    public String getAllUsers() {
        String users = "";
        for (User u : userManager.getUsers()) {
            users = users + u.getUsername() + '\n';
        }
        return users;
    }

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
