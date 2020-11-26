package controllers;

import entities.User;
import exceptions.UserNotFoundException;
import presenters.DeleteAccountPresenter;
import useCaseClasses.UserManager;

import java.util.UUID;

/**
 * A controller for handling inputs when try to delete a user
 */

public class DeleteAccountController {

    private final UserManager userManager;
    private final DeleteAccountPresenter presenter;

    /**
     * Creates a CreateAccountController with the given UserManager
     *
     * @param um - The userManager this controller will use
     */
    public DeleteAccountController(UserManager um, DeleteAccountPresenter deleteAccountPresenter){
        userManager = um;
        presenter = deleteAccountPresenter;
    }

    /**
     * Handles the input given by the user
     *
     * @param input the users input
     * @return an InputProcessResult enum that details what happened
     * as a result of the given input
     */
    public InputProcessResult getNextScreen(String input){

        if (input.equals("back")) {
            return InputProcessResult.BACK;
        }

        if (!presenter.getAllUsers().contains(input)) {
            return InputProcessResult.USER_NOT_FOUND;
        }

        UUID wanted_id = userManager.getUserID(input);
        userManager.removeUser(wanted_id);

        return InputProcessResult.SUCCESS;
    }

}
