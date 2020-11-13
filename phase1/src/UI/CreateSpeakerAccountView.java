package UI;

import controllers.CreateSpeakerAccountController;
import controllers.InputProcessResult;
import presenters.CreateSpeakerAccountPresenter;
import useCaseClasses.UserManager;

import java.util.Scanner;

public class CreateSpeakerAccountView extends ConsoleView {

    private CreateSpeakerAccountController controller;
    private CreateSpeakerAccountPresenter presenter;

    public CreateSpeakerAccountView(CreateSpeakerAccountController controller, CreateSpeakerAccountPresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;
    }

    @Override
    public ConsoleViewType runFlow(Scanner inputScanner) {
        System.out.println(presenter.getPreInputText());
        String input = inputScanner.nextLine();
        
        InputProcessResult result = controller.getNextScreen(input);

        String CreateSpeakerOutput = presenter.getInputResponseText(result);
        System.out.println(CreateSpeakerOutput);

        return getScreen(result);
    }

    private ConsoleViewType getScreen(InputProcessResult result) {
        switch (result) {
            case SUCCESS: return ConsoleViewType.MAIN_MENU;
            case BACK: return ConsoleViewType.MAIN_MENU;
            default: return ConsoleViewType.CREATE_SPEAKER_ACCOUNT;
        }
    }
}
