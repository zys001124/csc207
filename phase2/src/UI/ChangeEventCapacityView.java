package UI;

import controllers.ChangeEventCapacityController;
import controllers.InputProcessResult;
import presenters.ChangeEventCapacityPresenter;

import java.util.Scanner;

public class ChangeEventCapacityView extends GuiView {

    private final ChangeEventCapacityController controller;
    private final ChangeEventCapacityPresenter presenter;

    public ChangeEventCapacityView(ChangeEventCapacityController controller, ChangeEventCapacityPresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;
    }

    public SceneType runFlow(Scanner inputScanner) {
        System.out.println(presenter.getPreInputText());
        String event = inputScanner.nextLine();

//        InputProcessResult result = controller.eventCapacityChange(event);
//
//        String loginResultOutput = presenter.getInputResponseText(result);
//        System.out.println(loginResultOutput);
//
//        return getNextScreen(result);
        return null;
    }

    private SceneType getNextScreen(InputProcessResult result) {
        switch (result) {
            case BACK:
                return SceneType.MAIN_MENU;
            case SUCCESS:
                return SceneType.MAIN_MENU;
            default:
                return SceneType.CHANGE_CAPACITY;
        }

    }
}
