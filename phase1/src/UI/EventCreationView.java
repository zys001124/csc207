package UI;

import controllers.EventCreationController;
import controllers.InputProcessResult;
import presenters.EventCreationPresenter;

import java.util.Scanner;

public class EventCreationView extends ConsoleView {

    private EventCreationController controller;
    private EventCreationPresenter presenter;

    public EventCreationView(EventCreationController controller, EventCreationPresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;
    }

    @Override
    public ConsoleViewType runFlow(Scanner inputScanner) {
        System.out.println(presenter.getPreInputText());
        String input = inputScanner.nextLine();

        presenter.setInputResponse("");
        InputProcessResult result = controller.createEvent(input);

        String CreateEventOutput = presenter.getInputResponseText(result);
        System.out.println(CreateEventOutput);

        return getNextScreen(result);
    }

    private ConsoleViewType getNextScreen(InputProcessResult result) {
        switch (result) {
            case BACK: return ConsoleViewType.MAIN_MENU;
            case SUCCESS: return ConsoleViewType.MAIN_MENU;
            default: return ConsoleViewType.CREATE_EVENT;
        }
    }

}
