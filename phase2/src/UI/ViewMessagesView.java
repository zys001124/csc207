package UI;


import controllers.InputProcessResult;
import controllers.ViewMessagesController;
import presenters.ViewMessagesPresenter;

import java.util.Scanner;

/**
 * User Interface for the message history between two users
 */
public class ViewMessagesView extends GuiView {

    private final ViewMessagesController controller;
    private final ViewMessagesPresenter presenter;

    /**
     * initalizer for the message history view on what controller and presenter to use
     * @param controller the corresponding ViewMessageController
     * @param presenter the corresponding ViewMessagePresenter
     */
    public ViewMessagesView(ViewMessagesController controller, ViewMessagesPresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;
    }

    /**
     * the run flow for how this message history is going to work
     * @param inputScanner the Scanner that can be used to collect
     *                     input from the user
     * @return The ConsoleView that will lead to the corresponding screen
     */
    public SceneType runFlow(Scanner inputScanner){
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

    /**
     * used as a helper method to help navigate to the next possble screen depending on
     * param passes in
     * @param result the InputProcessResult that is called/processed from the runFlow
     * @return The ConsoleViewType for the next screen to be viewed.
     */
    private SceneType getNextScreen(InputProcessResult result) {
        if(result == InputProcessResult.BACK || result == InputProcessResult.SUCCESS
                || result == InputProcessResult.NO_MESSAGE_HISTORY){
            return SceneType.MAIN_MENU;
        }
        else{
            return SceneType.VIEW_MESSAGES;
        }
    }


}
