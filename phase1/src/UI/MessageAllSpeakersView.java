package UI;

import controllers.InputProcessResult;
import controllers.MessageAllSpeakersController;
import presenters.MessageAllSpeakersPresenter;

import java.util.Scanner;

public class MessageAllSpeakersView extends ConsoleView{

    private MessageAllSpeakersController controller;
    private MessageAllSpeakersPresenter presenter;

    public MessageAllSpeakersView(MessageAllSpeakersController controller, MessageAllSpeakersPresenter presenter){
        this.controller = controller;
        this.presenter = presenter;
    }

    @Override
    public ConsoleViewType runFlow(Scanner inputScanner) {
        System.out.println(presenter.messagePrompt());
        String message = inputScanner.nextLine();
        
        InputProcessResult result = controller.sendMessage(message);
        System.out.println(presenter.getMessageResult(result));
        return getNextScreen(result);
    }

    private ConsoleViewType getNextScreen(InputProcessResult result) {
        if(result.equals(InputProcessResult.SUCCESS) || result.equals(InputProcessResult.NAVIGATE_TO_MAIN_MENU)) {
            return ConsoleViewType.MAIN_MENU;
        }
        else {
            return ConsoleViewType.MESSAGE_ALL_SPEAKERS;
        }
    }
}
