package UI;

import controllers.InputProcessResult;
import controllers.MessageUserController;
import presenters.MessageUserPresenter;

import java.util.Scanner;

public class MessageUserView extends ConsoleView {

    private MessageUserController controller;
    private MessageUserPresenter presenter;


    public MessageUserView(MessageUserController controller, MessageUserPresenter presenter){
        System.out.println(presenter.getIntro());

        this.controller = controller;
        this.presenter = presenter;
    }

    @Override
    public ConsoleViewType runFlow(Scanner inputScanner) {
        InputProcessResult result = InputProcessResult.SUCCESS;
        return getNextScreen(result);
    }

    private ConsoleViewType getNextScreen(InputProcessResult result) {
        if(result == InputProcessResult.SUCCESS) {
            return ConsoleViewType.MAIN_MENU;
        }
        else {
            return ConsoleViewType.MESSAGE_USER;
        }
    }
}
