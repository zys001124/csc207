package UI;

import controllers.DeleteAccountController;
import controllers.InputProcessResult;
import exceptions.UserNotFoundException;
import presenters.DeleteAccountPresenter;

import java.util.Scanner;

/**
 * A ConsoleView that is responsible for displaying the information
 * needed for an Admin to cancel an existing user account.
 */

public class DeleteAccountView extends ConsoleView{

    private final DeleteAccountController controller;
    private final DeleteAccountPresenter presenter;

    /**
     * Creates an DeleteAccountView with the given DeleteAccountController and DeleteAccountPresenter
     *
     * @param controller The DeleteAccountController for handling inputs
     * @param presenter  The DeleteAccountPresenter for formatting the information
     *                   that should be displayed on the screen
     */

    public DeleteAccountView(DeleteAccountController controller, DeleteAccountPresenter presenter){
        this.controller = controller;
        this.presenter = presenter;
    }

    /**
     * Displays the menu of this ConsoleView and passes input to controller
     *
     * @param inputScanner the Scanner that can be used to collect
     *                     input from the user
     * @return a ConsoleViewType that represents what the next ConsoleView to
     * display should be.
     */

    public ConsoleViewType runFlow(Scanner inputScanner){
        System.out.println(presenter.getAllUsers());
        System.out.println(presenter.getPreInputText());
        String input = inputScanner.nextLine();

        InputProcessResult result = controller.getNextScreen(input);

        String DeleteAccountOutput = presenter.getInputResponseText(result);

        System.out.println(DeleteAccountOutput);

        return getScreen(result);
    }

    private ConsoleViewType getScreen(InputProcessResult result) {
        switch (result) {
            case BACK:
            case SUCCESS:
                return ConsoleViewType.MAIN_MENU;
            default:
                return ConsoleViewType.DELETE_ACCOUNT;
        }
    }

}
