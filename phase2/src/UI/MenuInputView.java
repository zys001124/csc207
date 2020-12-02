package UI;

import controllers.InputProcessResult;
import controllers.MenuInputController;
import presenters.MenuInputPresenter;

import java.util.Scanner;

/**
 * The main menu ConsoleView
 */
public class MenuInputView extends GuiView {

    private final MenuInputController menuInputController;
    private final MenuInputPresenter menuInputPresenter;

    /**
     * Creates a MenuInputView with the given MenuInputController and MenuInputPresenter
     *
     * @param menuInputController - The Controller this ConsoleView will use to handle input
     * @param menuInputPresenter  - The presenter this ConsoleView will use to get formatted
     *                            Strings for output
     */
    public MenuInputView(MenuInputController menuInputController, MenuInputPresenter menuInputPresenter) {
        this.menuInputController = menuInputController;
        this.menuInputPresenter = menuInputPresenter;
    }

    /**
     * Prints output for the ConsoleView and passes user input to <controller> for handling
     *
     * @param inputScanner the Scanner that can be used to collect
     *                     input from the user
     * @return a ConsoleViewType - specifies which kind of ConsoleView should be displayed
     * next as a result of the users input
     */
    @Override
    public SceneType runFlow(Scanner inputScanner) {
        System.out.println(menuInputPresenter.getPreInputText());
        String input = inputScanner.nextLine();

        InputProcessResult result = menuInputController.handleInput(input);
        SceneType nextScreenType = getScreen(result);

        if (result == InputProcessResult.INVALID_INPUT) {
            System.out.println(menuInputPresenter.getInputResponseText(result));
        }

        return nextScreenType;
    }

    private SceneType getScreen(InputProcessResult result) {
        if (result == null) {
            return null;
        }
        switch (result) {
            case NAVIGATE_TO_MESSAGE_USER:
                return SceneType.MESSAGE_USER;
            case NAVIGATE_TO_MESSAGE_ALL_ATTENDING_EVENT:
                return SceneType.MESSAGE_ALL_ATTENDING_EVENT;
            case NAVIGATE_TO_MESSAGE_ALL_SPEAKERS:
                return SceneType.MESSAGE_ALL_SPEAKERS;
            case NAVIGATE_TO_MESSAGE_ALL_ATTENDEES:
                return SceneType.MESSAGE_ALL_ATTENDEES;
            case NAVIGATE_TO_EVENT_SCHEDULE:
                return SceneType.EVENT_SCHEDULE;
            case NAVIGATE_TO_CREATE_EVENT:
                return SceneType.CREATE_EVENT;
            case NAVIGATE_TO_CANCEL_EVENT:
                return SceneType.CANCEL_EVENT;
            case NAVIGATE_TO_DELETE_USER_ACCOUNT:
                return SceneType.DELETE_ACCOUNT;
            case NAVIGATE_TO_ENROLL_IN_EVENT:
                return SceneType.ENROLL_IN_EVENT;
            case NAVIGATE_TO_UNENROLL_IN_EVENT:
                return SceneType.UNENROLL_IN_EVENT;
            case NAVIGATE_TO_CREATE_SPEAKER_ACCOUNT:
                return SceneType.CREATE_ACCOUNT;
            case NAVIGATE_TO_CHANGE_CAPACITY:
                return SceneType.CHANGE_CAPACITY;
            case NAVIGATE_TO_VIEW_MESSAGES:
                return SceneType.VIEW_MESSAGES;
            default:
                return SceneType.MAIN_MENU;
        }
    }
}
