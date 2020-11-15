package UI;

import controllers.InputProcessResult;
import controllers.MessageUserController;
import presenters.MessageUserPresenter;

import java.util.Scanner;

public class MessageUserView extends ConsoleView {

    private final MessageUserController controller;
    private final MessageUserPresenter presenter;


    public MessageUserView(MessageUserController controller, MessageUserPresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;
    }

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

    private ConsoleViewType getNextScreen(InputProcessResult result) {
        if (result.equals(InputProcessResult.SUCCESS) || result.equals(InputProcessResult.NAVIGATE_TO_MAIN_MENU)) {
            return ConsoleViewType.MAIN_MENU;
        } else {
            return ConsoleViewType.MESSAGE_USER;
        }
    }
}
