package UI;

import presenters.SeeSchedulePresenter;

import java.util.Scanner;

public class SeeScheduleView extends ConsoleView{
    private SeeSchedulePresenter presenter;

    public SeeScheduleView(SeeSchedulePresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public ConsoleViewType runFlow(Scanner inputScanner) {
        System.out.println(presenter.getPreInputText());

        System.out.print(presenter.outro());
        inputScanner.nextLine();

        return ConsoleViewType.MAIN_MENU;
    }
}
