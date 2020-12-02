package UI;

import controllers.InputProcessResult;
import controllers.MessageAllSpeakersController;
import presenters.MessageAllSpeakersPresenter;

import java.util.Scanner;

/**
 * A ConsoleView that is responsible for displaying and receiving the information
 * necessary for a user trying to message all speakers at the conference
 */
public class MessageAllSpeakersView extends GuiView {

    private final MessageAllSpeakersController controller;
    private final MessageAllSpeakersPresenter presenter;

    /**
     * Creates a MessageAllSpeakersView with the given MessageAllSpeakersController and MessageAllSpeakersPresenter
     *
     * @param controller - The controller this ConsoleView will use to handle input
     * @param presenter  - The presenter this ConsoleView will use to get formatted
     *                   Strings for output
     */
    public MessageAllSpeakersView(MessageAllSpeakersController controller, MessageAllSpeakersPresenter presenter) {
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
    public SceneType runFlow(Scanner inputScanner) {
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
    private SceneType getNextScreen(InputProcessResult result) {
        if (result.equals(InputProcessResult.SUCCESS) || result.equals(InputProcessResult.NAVIGATE_TO_MAIN_MENU)) {
            return SceneType.MAIN_MENU;
        } else {
            return SceneType.MESSAGE_ALL_SPEAKERS;
        }
    }
}
