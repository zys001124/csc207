package controllers;

import UI.ConsoleView;
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

    public ConsoleView.ConsoleViewType getNextScreen(String input) {

        if(input.equals("back")) {
            return ConsoleView.ConsoleViewType.MAIN_MENU;
        }

        String[] usernameAndPassword = input.split(" ");

        // Invalid input
        if(usernameAndPassword.length != 2) {
            presenter.setInputResponse("Expecting two, and only two inputs. Try again.");
            return ConsoleView.ConsoleViewType.CREATE_SPEAKER_ACCOUNT;
        }

        String username = usernameAndPassword[0];
        String password = usernameAndPassword[1];



        try {
            userManager.addUser(User.UserType.SPEAKER, username, password);
            presenter.setInputResponse("Speaker added successfully");
        } catch (UsernameAlreadyExistsException e) {
            presenter.setInputResponse("Username taken. Try again.");
        }

        return ConsoleView.ConsoleViewType.CREATE_SPEAKER_ACCOUNT;
    }
}
