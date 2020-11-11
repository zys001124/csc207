package UI;

import controllers.InputProcessResult;
import controllers.LoginController;
import controllers.MenuInputController;
import presenters.LoginPresenter;
import presenters.MenuInputPresenter;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

import java.util.Scanner;

public class LoginView extends ConsoleView {

    private LoginPresenter presenter;
    private LoginController controller;

    public LoginView(LoginController controller, LoginPresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;
    }

    @Override
    public ConsoleViewType runFlow(Scanner inputScanner) {
        System.out.println(presenter.getIntro());

        System.out.print(presenter.getUsernameInputPrompt());
        String username = inputScanner.nextLine();

        System.out.print(presenter.getPasswordInputPrompt());
        String password = inputScanner.nextLine();

        InputProcessResult result = controller.verifyLogin(username, password);

        String loginResultOutput = presenter.getLoginResultMessage(result);
        System.out.println(loginResultOutput);

        return getNextScreen(result);
    }

    private ConsoleViewType getNextScreen(InputProcessResult result) {
        if(result == InputProcessResult.SUCCESS) {
            return ConsoleViewType.MAIN_MENU;
        }
        else {
            return ConsoleViewType.LOGIN;
        }
    }
}
