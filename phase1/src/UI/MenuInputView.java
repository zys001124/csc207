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

        ConsoleViewType nextScreenType = menuInputController.getNextScreen(input);

        if(nextScreenType == ConsoleViewType.MAIN_MENU) {
            System.out.println(menuInputPresenter.getInvalidInputMessage());
        }

        return nextScreenType;
    }
}
