package UI;

import controllers.InputProcessResult;
import controllers.LoginController;
import presenters.LoginPresenter;

import java.util.Scanner;

/**
 * A ConsoleView for what should be displayed/collected from the user
 * at the login screen
 */
public class LoginView extends ConsoleView {

    private final LoginPresenter presenter;
    private final LoginController controller;

    /**
     * Creates a LoginView with the given LoginController and LoginPresenter
     *
     * @param controller - The LoginController that this view will use to
     *                   handle input on this view
     * @param presenter  - The LoginPresenter that this view will use to get
     *                   formatted output for this view to display
     */
    public LoginView(LoginController controller, LoginPresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;
    }

    /**
     * Prints output necessary to give the user instructions on how to log in.
     * Collects input from the user and passes it to <controller> for handling.
     *
     * @param inputScanner the Scanner that can be used to collect
     *                     input from the user
     * @return a ConsoleViewType - specifies which view should be displayed next
     * as a result of the users input
     */
    @Override
    public ConsoleViewType runFlow(Scanner inputScanner) {
        System.out.println(presenter.getPreInputText());

        System.out.print(presenter.getUsernameInputPrompt());
        String username = inputScanner.nextLine();

        System.out.print(presenter.getPasswordInputPrompt());
        String password = inputScanner.nextLine();

        InputProcessResult result = controller.verifyLogin(username, password);

        String loginResultOutput = presenter.getInputResponseText(result);
        System.out.println(loginResultOutput);

        return getNextScreen(result);
    }

    private ConsoleViewType getNextScreen(InputProcessResult result) {
        if (result == InputProcessResult.SUCCESS) {
            return ConsoleViewType.MAIN_MENU;
        } else {
            return ConsoleViewType.LOGIN;
        }
    }
}
