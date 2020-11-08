package UI;

import controllers.AttendeeMessageSelectionController;
import presenters.AttendeeMessageSelectionPresenter;

public class AttendeeMessageSelectionView {
    private AttendeeMessageSelectionController amController;
    private AttendeeMessageSelectionPresenter amPresenter;

    public AttendeeMessageSelectionView(AttendeeMessageSelectionController amController,
                                        AttendeeMessageSelectionPresenter amPresenter){
        this.amController = amController;
        this.amPresenter = amPresenter;
    }
}
