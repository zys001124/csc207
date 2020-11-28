package UI;


import controllers.InputProcessResult;
import controllers.ViewMessagesController;
import presenters.ViewMessagesPresenter;

import java.util.Scanner;

public class ViewMessagesView extends ConsoleView{

    private final ViewMessagesController controller;
    private final ViewMessagesPresenter presenter;

    public ViewMessagesView(ViewMessagesController controller, ViewMessagesPresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;
    }

    public ConsoleViewType runFlow(Scanner inputScanner){
        System.out.println(presenter.getPreInputText());
        System.out.println(presenter.getUsernames());
        String receiveUsername = inputScanner.nextLine();

        InputProcessResult result = controller.handleInput(receiveUsername);

        if(result == InputProcessResult.BACK){
            System.out.println(presenter.getInputResponseText(result));
            return getNextScreen(result);
        }
        else if(result == InputProcessResult.USER_NOT_FOUND){
            System.out.println(presenter.getInputResponseText(result));
            return getNextScreen(result);
        }
        else{
            System.out.println(presenter.preMessageHistoryText(receiveUsername));
            System.out.println(presenter.messageHistory(receiveUsername));
            System.out.println(presenter.getInputResponseText(result));
            String anyKey = inputScanner.nextLine();
            return getNextScreen(result);
        }




    }

    private ConsoleViewType getNextScreen(InputProcessResult result) {
        if(result == InputProcessResult.BACK || result == InputProcessResult.SUCCESS
                || result == InputProcessResult.NO_MESSAGE_HISTORY){
            return ConsoleViewType.MAIN_MENU;
        }
        else{
            return ConsoleViewType.VIEW_MESSAGES;
        }
    }


}
