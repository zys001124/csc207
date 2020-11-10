package UI;

import controllers.MessageAllAttendingEventController;
import presenters.MessageAllAttendingEventPresenter;

import java.util.Scanner;

public class MessageAllAttendingEventView {

    private MessageAllAttendingEventController controller;
    private MessageAllAttendingEventPresenter presenter;

    public MessageAllAttendingEventView(MessageAllAttendingEventController controller, MessageAllAttendingEventPresenter presenter){
        this.controller = controller;
        this.presenter = presenter;
    }

    public ConsoleView.ConsoleViewType runFlow(Scanner inputScanner){
        System.out.println(presenter.getIntro());
        String uuidInput = inputScanner.nextLine();

        System.out.println(presenter.getMessage());
        String message = inputScanner.nextLine();


        presenter.setInputResponse("");
        ConsoleView.ConsoleViewType nextScreen = controller.getNextScreen(uuidInput, message);
        System.out.println(presenter.getInputResponse());

        return nextScreen;
    }
}
