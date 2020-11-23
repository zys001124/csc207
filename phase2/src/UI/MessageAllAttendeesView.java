package UI;

import controllers.InputProcessResult;
import controllers.MessageAllAttendeesController;
import presenters.MessageAllAttendeesPresenter;

import java.util.Scanner;

/**
 * A ConsoleView that is responsible for displaying and receiving the information
 * necessary for a user trying to message all attendees at the conference
 */
public class MessageAllAttendeesView extends ConsoleView {

    private final MessageAllAttendeesController controller;
    private final MessageAllAttendeesPresenter presenter;

    /**
     * Creates a MessageAllAttendeesView with the given MessageAllAttendeesController and MessageAllAttendeesPresenter
     *
     * @param controller - The controller this ConsoleView will use to handle input
     * @param presenter  - The presenter this ConsoleView will use to get formatted
     *                   Strings for output
     */
    public MessageAllAttendeesView(MessageAllAttendeesController controller, MessageAllAttendeesPresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;
    }

    /**
     * Prints output specified through <presenter> for the ConsoleView and passes user input to <controller> for handling
     *
     * @param inputScanner - the Scanner that can be used to collect
     *                     input from the user
     * @return a ConsoleViewType = Specifies which kind of ConsoleView should be displayed
     * next as a result of the users input
     */
    @Override
    public ConsoleViewType runFlow(Scanner inputScanner) {
        System.out.println(presenter.getPreInputText());
        String message = inputScanner.nextLine();

        InputProcessResult result = controller.sendMessage(message);
        System.out.println(presenter.getInputResponseText(result));
        return getNextScreen(result);
    }

    /**
     * Processes the <result> and returns the appropriate ConsoleViewType which should be navigated to next
     *
     * @param result - The result from handling the user input
     * @return a ConsoleViewType = Specifies which kind of ConsoleView should be displayed next
     */
    private ConsoleViewType getNextScreen(InputProcessResult result) {
        if (result.equals(InputProcessResult.SUCCESS) || result.equals(InputProcessResult.NAVIGATE_TO_MAIN_MENU)) {
            return ConsoleViewType.MAIN_MENU;
        } else {
            return ConsoleViewType.MESSAGE_ALL_SPEAKERS;
        }
    }
}
