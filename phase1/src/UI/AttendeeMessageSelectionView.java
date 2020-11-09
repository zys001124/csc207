package UI;

import controllers.AttendeeMessageSelectionController;
import presenters.AttendeeMessageSelectionPresenter;

import java.util.Scanner;

public class AttendeeMessageSelectionView extends ConsoleView {

    private AttendeeMessageSelectionController amController;
    private AttendeeMessageSelectionPresenter amPresenter;

    public AttendeeMessageSelectionView(AttendeeMessageSelectionController amController,
                                        AttendeeMessageSelectionPresenter amPresenter){
        this.amController = amController;
        this.amPresenter = amPresenter;
    }

    @Override
    public ConsoleView handleInput(Scanner inputScanner) {
        return null;
    }
}
