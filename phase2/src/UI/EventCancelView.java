package UI;

import controllers.EventCancelController;
import controllers.InputProcessResult;
import presenters.EventCancelPresenter;

import java.util.Scanner;

/**
 * A ConsoleView that is responsible for displaying the information
 * needed for an organizer to cancel an existing event.
 */

public class EventCancelView extends ConsoleView {

    private final EventCancelPresenter presenter;
    private final EventCancelController controller;

    /**
     * Creates an EventCancelView with the given EventCancelController and EventCancelPresenter
     *
     * @param controller The EventCancelController for handling inputs
     * @param presenter  The EventCancelPresenter for formatting the information
     *                   that should be displayed on the screen
     */

    public EventCancelView(EventCancelController controller, EventCancelPresenter presenter) {
        this.presenter = presenter;
        this.controller = controller;
    }

    /**
     * Displays the menu of this ConsoleView and passes input to controller
     *
     * @param inputScanner the Scanner that can be used to collect
     *                     input from the user
     * @return a ConsoleViewType that represents what the next ConsoleView to
     * display should be.
     */

    @Override
    public ConsoleViewType runFlow(Scanner inputScanner) {
        System.out.println(presenter.getAllEvents());
        System.out.println(presenter.getPreInputText());
        String input = inputScanner.nextLine();

        InputProcessResult result = controller.getNextScreen(input);

        String CancelEventOutput = presenter.getInputResponseText(result);

        System.out.println(CancelEventOutput);

        return getScreen(result);
    }

    private ConsoleViewType getScreen(InputProcessResult result) {
        switch (result) {
            case BACK:
            case SUCCESS:
                return ConsoleViewType.MAIN_MENU;
            default:
                return ConsoleViewType.CANCEL_EVENT;
        }
    }
}
