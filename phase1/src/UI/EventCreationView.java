package UI;

import controllers.EventCreationController;
import presenters.EventCreationPresenter;

import java.util.Scanner;

public class EventCreationView extends ConsoleView{

    private EventCreationPresenter presenter;
    private EventCreationController controller;

    public EventCreationView(EventCreationController controller, EventCreationPresenter presenter){
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
