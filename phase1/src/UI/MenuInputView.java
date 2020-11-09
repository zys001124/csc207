package UI;

import controllers.MenuInputController;
import entities.User;
import exceptions.InvalidInputException;
import presenters.MenuInputPresenter;

import java.util.Scanner;

public class MenuInputView extends ConsoleView {

    private MenuInputController menuInputController;
    private MenuInputPresenter menuInputPresenter;
    private User.UserType userType;

    public MenuInputView(MenuInputController menuInputController, MenuInputPresenter menuInputPresenter, User.UserType userType)
    {
        this.menuInputController = menuInputController;
        this.menuInputPresenter = menuInputPresenter;
        this.userType = userType;
    }

    @Override
    public ConsoleViewType runFlow(Scanner inputScanner) {
        System.out.println(menuInputPresenter.getMenuOptions(userType));
        String input = inputScanner.nextLine();

        //TODO finish
        try {
            return menuInputController.getNextScreen(input);
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
        return null;
    }
}
