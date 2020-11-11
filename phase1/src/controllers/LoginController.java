package controllers;

import entities.User;
import exceptions.IncorrectPasswordException;
import exceptions.UserNotFoundException;
//import jdk.internal.util.xml.impl.Input;
import presenters.LoginPresenter;
import useCaseClasses.UserManager;


public class LoginController {

    private UserManager manager;

    public LoginController(UserManager manager) {
        this.manager = manager;
    }

    public InputProcessResult verifyLogin(String username, String password) {
        try {
            manager.userLogin(username, password);
        } catch (IncorrectPasswordException e) {
            return InputProcessResult.INCORRECT_PASSWORD;
        } catch (UserNotFoundException e) {
            return InputProcessResult.USER_NOT_FOUND;
        }

        return InputProcessResult.SUCCESS;
    }

}
