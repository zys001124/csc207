package UI;

import controllers.EventEnrollController;
import controllers.InputProcessResult;
import presenters.EventEnrollPresenter;

import java.util.Scanner;

/**
 * A ConsoleView that is responsible for displaying/collecting the
 * information necessary for an Attendee to enroll in an Event
 */
public class EventEnrollView extends ConsoleView {
    private final EventEnrollPresenter presenter;
    private final EventEnrollController controller;

    /**
     * Create an EventEnrollView with the given EventEnrollController and EventEnrollPresenter
     *
     * @param controller The EventEnrollController to be used for handling input
     * @param presenter  The EventEnrollPresenter to be used for formatting the strings that
     *                   should be printed to the console
     */
    public EventEnrollView(EventEnrollController controller, EventEnrollPresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;
    }

    /**
     * Displays the interface of EventEnrollView and passes input to the controller
     *
     * @param inputScanner the Scanner that can be used to collect
     *                     input from the user
     * @return A ConsoleViewType that represents what the next ConsoleView to display
     * should be.
     */
    @Override
    public ConsoleViewType runFlow(Scanner inputScanner) {
        System.out.println(presenter.getPreInputText());
        System.out.println(presenter.getAllEvents());

        System.out.print(presenter.getEventNumberInputPrompt());
        String eventNumber = inputScanner.nextLine();

        InputProcessResult result = controller.enrollEvent(eventNumber);

        String enrollResultOutput = presenter.getInputResponseText(result);
        System.out.println(enrollResultOutput);

        return getNextScreen(result);
    }

    private ConsoleViewType getNextScreen(InputProcessResult result) {
        if (result == InputProcessResult.SUCCESS) {
            return ConsoleViewType.MAIN_MENU;
        } else {
            return ConsoleViewType.ENROLL_IN_EVENT;
        }
    }
}
