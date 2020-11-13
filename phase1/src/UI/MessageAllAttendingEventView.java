package UI;

import controllers.InputProcessResult;
import controllers.MessageAllAttendingEventController;
import presenters.MessageAllAttendingEventPresenter;

import java.util.Scanner;

public class MessageAllAttendingEventView extends ConsoleView{

    private MessageAllAttendingEventController controller;
    private MessageAllAttendingEventPresenter presenter;

    public MessageAllAttendingEventView(MessageAllAttendingEventController controller, MessageAllAttendingEventPresenter presenter){
        this.controller = controller;
        this.presenter = presenter;
    }

    public ConsoleViewType runFlow(Scanner inputScanner){
        System.out.println(presenter.getPreInputText());
        String eventName = inputScanner.nextLine();

        InputProcessResult findBack = controller.findBack(eventName);
        if(findBack == InputProcessResult.BACK){
            return getNextScreen(findBack);
        }
        else {

            System.out.println(presenter.getMessage());
            String message = inputScanner.nextLine();

            InputProcessResult result = controller.handleInput(eventName, message);

            String resultOutput = presenter.getInputResponseText(result);
            System.out.println(resultOutput);

            return getNextScreen(result);
        }
    }

    private ConsoleViewType getNextScreen(InputProcessResult result) {
        if(result == InputProcessResult.SUCCESS) {
            return ConsoleViewType.MAIN_MENU;
        }
        else if(result == InputProcessResult.BACK){
            return ConsoleViewType.MAIN_MENU;
        }
        else {
            return ConsoleViewType.MESSAGE_ALL_ATTENDING_EVENT;
        }
    }
}
