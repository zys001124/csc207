package UI;

import presenters.SeeSchedulePresenter;

import java.util.Scanner;

/**
 * A ConsoleView that is responsible for displaying the information
 * necessary for an Attendee to see his/her event schedule
 */
public class SeeScheduleView extends ConsoleView {
    private final SeeSchedulePresenter presenter;

    /**
     * Create a SeeSchduleView with the given SeeSchedulePresenter
     *
     * @param presenter The SeeSchedulePresenter to be used for formatting the strings
     *                  that should be printed to the console
     */
    public SeeScheduleView(SeeSchedulePresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Displays the interface of SeeScheduleView
     *
     * @param inputScanner the Scanner that can be used to collect
     *                     input from the user
     * @return A ConsoleViewType -- MAIN.MENu
     */
    @Override
    public ConsoleViewType runFlow(Scanner inputScanner) {
        System.out.println(presenter.getPreInputText());

        System.out.print(presenter.outro());
        inputScanner.nextLine();

        return ConsoleViewType.MAIN_MENU;
    }
}
