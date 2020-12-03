package UI;

import controllers.DeleteAccountController;
import controllers.InputProcessResult;
import presenters.DeleteAccountPresenter;

import java.util.Scanner;

public class DeleteAccountView extends GuiView {

    private final DeleteAccountController controller;
    private final DeleteAccountPresenter presenter;

    public DeleteAccountView(DeleteAccountController controller, DeleteAccountPresenter presenter){
        this.controller = controller;
        this.presenter = presenter;
    }

    public SceneType runFlow(Scanner inputScanner){
        System.out.println(presenter.getAllUsers());
        System.out.println(presenter.getPreInputText());
        String input = inputScanner.nextLine();

//        InputProcessResult result = controller.getNextScreen(input);
//
//        String DeleteAccountOutput = presenter.getInputResponseText(result);
//
//        System.out.println(DeleteAccountOutput);

        //return getScreen(result);
        return null;
    }

    private SceneType getScreen(InputProcessResult result) {
        switch (result) {
            case BACK:
            case SUCCESS:
                return SceneType.MAIN_MENU;
            default:
                return SceneType.DELETE_ACCOUNT;
        }
    }

}
