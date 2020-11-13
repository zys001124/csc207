package UI;

import controllers.InputProcessResult;
import controllers.MenuInputController;
import entities.User;
import exceptions.InvalidInputException;
import presenters.MenuInputPresenter;

import java.util.Scanner;

public class MenuInputView extends ConsoleView {

    private MenuInputController menuInputController;
    private MenuInputPresenter menuInputPresenter;

    public MenuInputView(MenuInputController menuInputController, MenuInputPresenter menuInputPresenter)
    {
        this.menuInputController = menuInputController;
        this.menuInputPresenter = menuInputPresenter;
    }

    @Override
    public ConsoleViewType runFlow(Scanner inputScanner) {
        System.out.println(menuInputPresenter.getPreInputText());
        String input = inputScanner.nextLine();

        InputProcessResult result = menuInputController.handleInput(input);
        ConsoleViewType nextScreenType = getScreen(result);

        if(result == InputProcessResult.INVALID_INPUT) {
            System.out.println(menuInputPresenter.getInputResponseText(result));
        }

        return nextScreenType;
    }

    private ConsoleViewType getScreen(InputProcessResult result) {
        if(result == null) {
            return null;
        }
        switch (result) {
            case NAVIGATE_TO_MESSAGE_USER: return ConsoleViewType.MESSAGE_USER;
            case NAVIGATE_TO_MESSAGE_ALL_ATTENDING_EVENT: return ConsoleViewType.MESSAGE_ALL_ATTENDING_EVENT;
            case NAVIGATE_TO_MESSAGE_ALL_SPEAKERS: return ConsoleViewType.MESSAGE_ALL_SPEAKERS;
            case NAVIGATE_TO_MESSAGE_ALL_ATTENDEES: return ConsoleViewType.MESSAGE_ALL_ATTENDEES;
            case NAVIGATE_TO_EVENT_SCHEDULE: return ConsoleViewType.EVENT_SCHEDULE;
            case NAVIGATE_TO_CREATE_EVENT: return ConsoleViewType.CREATE_EVENT;
            case NAVIGATE_TO_CANCEL_EVENT: return ConsoleViewType.CANCEL_EVENT;
            case NAVIGATE_TO_ENROLL_IN_EVENT: return ConsoleViewType.ENROLL_IN_EVENT;
            case NAVIGATE_TO_UNENROLL_IN_EVENT: return ConsoleViewType.UNENROLL_IN_EVENT;
            case NAVIGATE_TO_CREATE_SPEAKER_ACCOUNT: return ConsoleViewType.CREATE_SPEAKER_ACCOUNT;
            default: return ConsoleViewType.MAIN_MENU;
        }
    }
}
