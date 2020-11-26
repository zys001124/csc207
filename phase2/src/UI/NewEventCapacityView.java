package UI;

import controllers.ChangeCapacityController;
import controllers.InputProcessResult;
import presenters.ChangeEventCapacityPresenter;

import java.util.Scanner;

public class NewEventCapacityView extends ConsoleView{

    private final ChangeCapacityController controller;
    private final ChangeEventCapacityPresenter presenter;

    public NewEventCapacityView(ChangeCapacityController controller, ChangeEventCapacityPresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;
    }

    public ConsoleViewType runFlow(Scanner inputScanner) {
        System.out.println(presenter.getPreInputText());
        String event = inputScanner.nextLine();

        InputProcessResult result = controller.eventCapacityChange(event);

        String loginResultOutput = presenter.getInputResponseText(result);
        System.out.println(loginResultOutput);

        return getNextScreen(result);
    }

    private ConsoleViewType getNextScreen(InputProcessResult result){
        switch (result) {
            case BACK:
                return ConsoleViewType.MAIN_MENU;
            case SUCCESS:
                return ConsoleViewType.MAIN_MENU;
            default:
                return ConsoleViewType.CHANGE_CAPACITY;
        }

    }
}
