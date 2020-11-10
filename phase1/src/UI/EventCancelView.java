package UI;

import controllers.EventCancelController;
import presenters.EventCancelPresenter;

import java.util.Scanner;

public class EventCancelView extends ConsoleView {

    private EventCancelPresenter presenter;
    private EventCancelController controller;

    public EventCancelView(EventCancelController controller, EventCancelPresenter presenter){
        this.presenter = presenter;
        this.controller = controller;
    }

    @Override
    public ConsoleViewType runFlow(Scanner inputScanner){
        System.out.println(presenter.getIntro());
        String input = inputScanner.nextLine();

        presenter.setInputResponse("");
        ConsoleViewType nextScreenType = controller.getNextScreen(input);
        System.out.println(presenter.getInputResponse());

        return nextScreenType;
    }
}
