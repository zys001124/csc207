package controllers;

import entities.User;
import exceptions.UsernameAlreadyExistsException;
import presenters.CreateSpeakerAccountPresenter;
import useCaseClasses.UserManager;

import java.util.UUID;

public class CreateSpeakerAccountController {

    private UserManager userManager;
    private CreateSpeakerAccountPresenter presenter;

    public CreateSpeakerAccountController(UserManager um, CreateSpeakerAccountPresenter presenter) {
        userManager = um;
        this.presenter = presenter;
    }

    public InputProcessResult getNextScreen(String input) {

        if(input.equals("back")) {
            return InputProcessResult.BACK;
        }

        String[] usernameAndPassword = input.split(" ");

        // Invalid input
        if(usernameAndPassword.length != 2) {
            return InputProcessResult.INVALID_INPUT;
        }

        String username = usernameAndPassword[0];
        String password = usernameAndPassword[1];

        try {
            userManager.addUser(User.UserType.SPEAKER, username, password);
            return InputProcessResult.SUCCESS;
        } catch (UsernameAlreadyExistsException e) {
            return InputProcessResult.USERNAME_TAKEN;
        }

    }

}
