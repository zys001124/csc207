package UI;

import controllers.InputProcessResult;
import controllers.MessageAllAttendingEventController;
import presenters.MessageAllAttendingEventPresenter;

import java.util.Scanner;

public class MessageAllAttendingEventView extends ConsoleView {

    private final MessageAllAttendingEventController controller;
    private final MessageAllAttendingEventPresenter presenter;

    /**
     * MessageAllAttendingEventPresenter/Controller are put in to make sure that this UI follows those
     * presenters and controllers
     *
     * @param controller the corresponding MessageALlAttendingEventController for the UI
     * @param presenter  the corresponding MessageAllAttendingEventPresenter for the UI
     */
    public MessageAllAttendingEventView(MessageAllAttendingEventController controller, MessageAllAttendingEventPresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;
    }

    /**
     * The entire run flow for the screen. Prompts the user to input an event name, checks to see
     * if the event name exists. If SUCCESS program continues, if BACK then program goes back to
     * the main menu.
     * The flow then promps the user to input a message to send to all the attendees of the event.
     * If the event can't be found then the program goes back to the beginning of the runflow and promps
     * the user to put in a valid event that they are hosting. If SUCCESS, the message will be sent
     * and the user will be directed back to the main menu.
     *
     * @param inputScanner the Scanner for the user input
     * @return returns the next screen the user will see depending on if they want to go back to the
     * main menu, if they successfully put in their event and message, or if they don't succeed and need
     * to go back to the messageMenu.
     */
    public ConsoleViewType runFlow(Scanner inputScanner) {
        System.out.println(presenter.getPreInputText());
        String eventName = inputScanner.nextLine();

        InputProcessResult findBack = controller.findBack(eventName);
        if (findBack == InputProcessResult.BACK) {
            return getNextScreen(findBack);
        } else {

            System.out.println(presenter.getMessage());
            String message = inputScanner.nextLine();

            InputProcessResult result = controller.handleInput(eventName, message);

            String resultOutput = presenter.getInputResponseText(result);
            System.out.println(resultOutput);

            return getNextScreen(result);
        }
    }

    /**
     * helper method for the run flow to get the next screen based off of the controller success
     * or failures
     *
     * @param result the InputProcessResult that is given from the controller based on if the input
     *               is valid or not
     * @return A ConsoleViewType that takes the user to the specified menu option
     */
    private ConsoleViewType getNextScreen(InputProcessResult result) {
        if (result == InputProcessResult.SUCCESS) {
            return ConsoleViewType.MAIN_MENU;
        } else if (result == InputProcessResult.BACK) {
            return ConsoleViewType.MAIN_MENU;
        } else {
            return ConsoleViewType.MESSAGE_ALL_ATTENDING_EVENT;
        }
    }
}
