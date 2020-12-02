package UI;

import controllers.EventUnEnrollController;
import controllers.InputProcessResult;
import presenters.EventUnEnrollPresenter;

import java.util.Scanner;

/**
 * A ConsoleView that is responsible for displaying/collecting the
 * information necessary for an Attendee to un-enroll in an Event
 */
public class EventUnEnrollView extends GuiView {
    private final EventUnEnrollController controller;
    private final EventUnEnrollPresenter presenter;

    /**
     * Create an EventUnEnrollView with the given EventUnEnrollController and EventUnEnrollPresenter
     *
     * @param controller The EventUnEnrollController to be used for handling input
     * @param presenter  The EventUnEnrollPresenter to be used for formatting the strings that
     *                   should be printed to the console
     */
    public EventUnEnrollView(EventUnEnrollController controller, EventUnEnrollPresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;
    }

    /**
     * Displays the interface of EventUnEnrollView and passes input to the controller
     *
     * @param inputScanner the Scanner that can be used to collect
     *                     input from the user
     * @return A ConsoleViewType that represents what the next ConsoleView to display
     * should be.
     */
    @Override
    public SceneType runFlow(Scanner inputScanner) {
        System.out.println(presenter.getPreInputText());
        System.out.println(presenter.getAttendeeAllEvents());

        System.out.print(presenter.getEventNumberInputPrompt());
        String eventNumber = inputScanner.nextLine();

        InputProcessResult result = controller.unEnrollEvent(eventNumber);

        String unEnrollResultOutput = presenter.getInputResponseText(result);
        System.out.println(unEnrollResultOutput);

        return getNextScreen(result);
    }

    private SceneType getNextScreen(InputProcessResult result) {
        if (result == InputProcessResult.SUCCESS) {
            return SceneType.MAIN_MENU;
        } else {
            return SceneType.UNENROLL_IN_EVENT;
        }
    }
}
