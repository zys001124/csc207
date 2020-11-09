package UI;

import controllers.CreateSpeakerAccountController;
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
        System.out.println(presenter.getIntro());
        String input = inputScanner.nextLine();

        presenter.setInputResponse("");
        ConsoleViewType nextScreenType = controller.getNextScreen(input);
        System.out.println(presenter.getInputResponse());

        return nextScreenType;
    }
}
