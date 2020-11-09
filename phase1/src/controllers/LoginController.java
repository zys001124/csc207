package controllers;

import entities.User;
import exceptions.IncorrectPasswordException;
import exceptions.UserNotFoundException;
import presenters.LoginPresenter;
import useCaseClasses.UserManager;


public class LoginController {

    private UserManager manager;

    public LoginController(UserManager manager) {
        this.manager = manager;
    }

    public LoginResult verifyLogin(String username, String password) {
        try {
            manager.userLogin(username, password);
        } catch (IncorrectPasswordException e) {
            return LoginResult.INCORRECT_PASSWORD;
        } catch (UserNotFoundException e) {
            return LoginResult.USER_NOT_FOUND;
        }

        return LoginResult.SUCCESS;
    }

    public enum LoginResult {
        SUCCESS,
        INCORRECT_PASSWORD,
        USER_NOT_FOUND
    }
}
