package UI;

import controllers.EventListForMessagingController;
import controllers.InputProcessResult;
import presenters.EventListForMessagingPresenter;

import java.util.Scanner;

public class EventListForMessagingView extends ConsoleView{

    private EventListForMessagingController controller;
    private EventListForMessagingPresenter presenter;

    public EventListForMessagingView(EventListForMessagingController controller, EventListForMessagingPresenter presenter){
        this.controller = controller;
        this.presenter = presenter;
    }

    public ConsoleViewType runFlow(Scanner inputScanner){
        System.out.println(presenter.getIntro());
        String input = inputScanner.nextLine();

        InputProcessResult result = controller.getNextScreen(input);
        System.out.println(presenter.getInputResponse(result));

        return getNextScreen(result);

    }

    private ConsoleViewType getNextScreen(InputProcessResult result) {
        if(result == InputProcessResult.NAVIGATE_TO_EVENT_LIST_FOR_MESSAGING) {
            return ConsoleViewType.EVENT_LIST_FOR_MESSAGING;
        }
        else {
            return ConsoleViewType.MAIN_MENU;
        }
    }
}
