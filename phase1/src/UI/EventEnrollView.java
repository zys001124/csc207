package UI;

import controllers.EventEnrollController;
import presenters.EventEnrollPresenter;

import java.util.Scanner;

public class EventEnrollView extends ConsoleView {
    private EventEnrollPresenter presenter;
    private EventEnrollController controller;

    public EventEnrollView(EventEnrollController controller, EventEnrollPresenter presenter){
        this.controller = controller;
        this.presenter = presenter;
    }

    @Override
    public ConsoleViewType runFlow(Scanner inputScanner) {
        System.out.println(presenter.enrollIntro());
        System.out.println(presenter.getAllEvents());

        System.out.print(presenter.getEventNumberInputPrompt());
        String eventName = inputScanner.nextLine();

        EventEnrollController.EnrollResult result = controller.enrollEvent(eventName);

        String EnrollResultOutput = presenter.getEnrollResultMessage(result);
        System.out.println(EnrollResultOutput);

        return getNextScreen(result);
    }

    private ConsoleViewType getNextScreen(EventEnrollController.EnrollResult result) {
        if(result == EventEnrollController.EnrollResult.SUCCESS) {
            return ConsoleViewType.MAIN_MENU;
        }
        else {
            return ConsoleViewType.ENROLL_IN_EVENT;
        }
    }
}
