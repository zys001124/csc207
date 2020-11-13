package UI;

import controllers.EventEnrollController;
import controllers.InputProcessResult;
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
        String eventNumber = inputScanner.nextLine();

        InputProcessResult result = controller.enrollEvent(eventNumber);

        String enrollResultOutput = presenter.getEnrollResultMessage(result);
        System.out.println(enrollResultOutput);

        return getNextScreen(result);
    }

    private ConsoleViewType getNextScreen(InputProcessResult result) {
        if(result == InputProcessResult.SUCCESS) {
            return ConsoleViewType.MAIN_MENU;
        }
        else {
            return ConsoleViewType.ENROLL_IN_EVENT;
        }
    }
}
