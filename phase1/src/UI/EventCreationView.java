package UI;

import controllers.EventCreationController;
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
        return null;
    }
}
