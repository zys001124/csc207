package UI;

import controllers.DeleteAccountController;
import controllers.InputProcessResult;
import exceptions.UserNotFoundException;
import presenters.DeleteAccountPresenter;

import java.util.Scanner;

public class DeleteAccountView extends ConsoleView{

    private final DeleteAccountController controller;
    private final DeleteAccountPresenter presenter;

    public DeleteAccountView(DeleteAccountController controller, DeleteAccountPresenter presenter){
        this.controller = controller;
        this.presenter = presenter;
    }

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
