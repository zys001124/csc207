package UI;

import controllers.EventCancelController;
import controllers.InputProcessResult;
import presenters.EventCancelPresenter;

import java.util.Scanner;
import java.util.SortedMap;

public class EventCancelView extends ConsoleView {

    private EventCancelPresenter presenter;
    private EventCancelController controller;

    public EventCancelView(EventCancelController controller, EventCancelPresenter presenter){
        this.presenter = presenter;
        this.controller = controller;
    }

    @Override
    public ConsoleViewType runFlow(Scanner inputScanner){
        System.out.println(presenter.getAllEvents());
        System.out.println(presenter.getIntro());
        String input = inputScanner.nextLine();

        presenter.setInputResponse("");
        InputProcessResult result = controller.getNextScreen(input);

        ConsoleViewType nextScreenType = getScreen(result);

        System.out.println(presenter.getInputResponse());

        return nextScreenType;
    }

    private ConsoleViewType getScreen(InputProcessResult result) {
        switch (result) {
            case BACK: return ConsoleViewType.MAIN_MENU;
            case SUCCESS: return ConsoleViewType.MAIN_MENU;
            default: return ConsoleViewType.CANCEL_EVENT;
        }
    }
}
