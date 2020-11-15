package UI;

import controllers.InputProcessResult;
import controllers.MessageAllAttendeesController;
import presenters.MessageAllAttendeesPresenter;

import java.util.Scanner;

public class MessageAllAttendeesView extends ConsoleView {

    private final MessageAllAttendeesController controller;
    private final MessageAllAttendeesPresenter presenter;

    public MessageAllAttendeesView(MessageAllAttendeesController controller, MessageAllAttendeesPresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;
    }

    @Override
    public ConsoleViewType runFlow(Scanner inputScanner) {
        System.out.println(presenter.getPreInputText());
        String message = inputScanner.nextLine();

        InputProcessResult result = controller.sendMessage(message);
        System.out.println(presenter.getInputResponseText(result));
        return getNextScreen(result);
    }

    private ConsoleViewType getNextScreen(InputProcessResult result) {
        if (result.equals(InputProcessResult.SUCCESS) || result.equals(InputProcessResult.NAVIGATE_TO_MAIN_MENU)) {
            return ConsoleViewType.MAIN_MENU;
        } else {
            return ConsoleViewType.MESSAGE_ALL_SPEAKERS;
        }
    }
}
