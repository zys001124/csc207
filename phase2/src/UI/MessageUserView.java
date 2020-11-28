package UI;

import controllers.InputProcessResult;
import controllers.MessageUserController;
import presenters.MessageUserPresenter;

import java.util.Scanner;

/**
 * A ConsoleView that is responsible for displaying and receiving the information
 * necessary for a user trying to message an individual user
 */
public class MessageUserView extends ConsoleView {

    private final MessageUserController controller;
    private final MessageUserPresenter presenter;


    /**
     * Creates a MessageUserView with the given MessageUserController and MessageUserPresenter
     *
     * @param controller - The controller this ConsoleView will use to handle input
     * @param presenter  - The presenter this ConsoleView will use to get formatted
     *                   Strings for output
     */
    public MessageUserView(MessageUserController controller, MessageUserPresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;
    }

    /**
     * Prints output specified through <presenter> for the ConsoleView and passes user input to <controller> for handling
     * Will begin by outputting the initial intro, and then displaying a list of possible usernames to message. The user is
     * then expected to enter the username. Next, the user will be prompted to enter the message text. After the user has
     * entered the message text, <controller> will process the input and return an appropriate result to be handled by
     * handleInput, will finally indicate the next ConsoleViewType to be used next.
     *
     * @param inputScanner - the Scanner that can be used to collect
     *                     input from the user
     * @return a ConsoleViewType = Specifies which kind of ConsoleView should be displayed
     * next as a result of the users input
     */
    @Override
    public ConsoleViewType runFlow(Scanner inputScanner) {
        System.out.println(presenter.getPreInputText());
        System.out.println(presenter.getPossibleUsers());
        String receiverUsername = inputScanner.nextLine();

        System.out.println(presenter.messagePrompt());
        String messageText = inputScanner.nextLine();

        InputProcessResult result = controller.sendMessage(receiverUsername, messageText);
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
            return ConsoleViewType.MESSAGE_USER;
        }
    }
}
