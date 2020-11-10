package UI;

import controllers.EventListForMessagingController;
import presenters.EventListForMessagingPresenter;

import java.util.Scanner;

public class EventListForMessagingView {

    private EventListForMessagingController controller;
    private EventListForMessagingPresenter presenter;

    public EventListForMessagingView(EventListForMessagingController controller, EventListForMessagingPresenter presenter){
        this.controller = controller;
        this.presenter = presenter;
    }

    public ConsoleView.ConsoleViewType runFlow(Scanner inputScanner){
        System.out.println(presenter.getIntro());
        String input = inputScanner.nextLine();

        ConsoleView.ConsoleViewType nextScreen = controller.getNextScreen(input);
        System.out.println(presenter.getInputResponse());
        return nextScreen;

    }
}
