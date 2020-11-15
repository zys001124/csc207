package UI;

import controllers.EventCreationController;
import controllers.InputProcessResult;
import presenters.EventCreationPresenter;

import java.util.Scanner;

/**
 * A ConsoleView that is responsible for displaying/collection
 * the information necessary for an Organizer to create an Event
 */
public class EventCreationView extends ConsoleView {

    private final EventCreationController controller;
    private final EventCreationPresenter presenter;

    /**
     * Creates a EventCreationView with the given controller and presenter
     *
     * @param controller - The EventCreationController to be used for handling input
     * @param presenter  - The EventCreation to be used for formatting the
     *                   strings that should be printed to the console
     */
    public EventCreationView(EventCreationController controller, EventCreationPresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;
    }

    /**
     * Displays the interface of the ConsoleView and passes input to <controller>
     *
     * @param inputScanner the Scanner that can be used to collect
     *                     input from the user
     * @return a ConsoleViewType that represents what the next ConsoleView to
     * display should be.
     */
    @Override
    public ConsoleViewType runFlow(Scanner inputScanner) {
        System.out.println(presenter.getPreInputText());
        String input = inputScanner.nextLine();

        InputProcessResult result = controller.createEvent(input);

        String CreateEventOutput = presenter.getInputResponseText(result);
        System.out.println(CreateEventOutput);

        return getNextScreen(result);
    }

    private ConsoleViewType getNextScreen(InputProcessResult result) {
        switch (result) {
            case BACK:
                return ConsoleViewType.MAIN_MENU;
            case SUCCESS:
                return ConsoleViewType.MAIN_MENU;
            default:
                return ConsoleViewType.CREATE_EVENT;
        }
    }

}
