package UI;

import controllers.CreateSpeakerAccountController;
import controllers.InputProcessResult;
import presenters.CreateSpeakerAccountPresenter;

import java.util.Scanner;

/**
 * A ConsoleView that is responsible for displaying/collection
 * the information necessary for an Organizer to create a
 * Speaker account
 */
public class CreateSpeakerAccountView extends ConsoleView {

    private final CreateSpeakerAccountController controller;
    private final CreateSpeakerAccountPresenter presenter;

    /**
     * Creates a CreateSpeakerAccountView with the given controller and presenter
     *
     * @param controller - The CreateSpeakerAccountController to be used for handling input
     * @param presenter  - The CreateSpeakerAccountPresenter to be used for formatting the
     *                   strings that should be printed to the console
     */
    public CreateSpeakerAccountView(CreateSpeakerAccountController controller, CreateSpeakerAccountPresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;
    }

    /**
     * Displays the interface of the ConsoleView and passes input to <controller>
     *
     * @param inputScanner the Scanner that can be used to collect
     *                     input from the user
     * @return a ConsoleViewType that represents what the next ConsoleView to
     * display should be.
     */
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
            case SUCCESS:
            case BACK:
                return ConsoleViewType.MAIN_MENU;
            default:
                return ConsoleViewType.CREATE_SPEAKER_ACCOUNT;
        }
    }
}
