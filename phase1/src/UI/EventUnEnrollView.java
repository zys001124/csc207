package UI;

import controllers.InputProcessResult;
import controllers.EventUnEnrollController;
import presenters.EventUnEnrollPresenter;

import java.util.Scanner;

public class EventUnEnrollView extends ConsoleView{
    private EventUnEnrollController controller;
    private EventUnEnrollPresenter presenter;

    public EventUnEnrollView(EventUnEnrollController controller, EventUnEnrollPresenter presenter){
        this.controller = controller;
        this.presenter = presenter;
    }

    @Override
    public ConsoleViewType runFlow(Scanner inputScanner) {
        System.out.println(presenter.unEnrollIntro());
        System.out.println(presenter.getAttendeeAllEvents());

        System.out.print(presenter.getEventNumberInputPrompt());
        String eventNumber = inputScanner.nextLine();

        InputProcessResult result = controller.unEnrollEvent(eventNumber);

        String unEnrollResultOutput = presenter.getUnEnrollResultMessage(result);
        System.out.println(unEnrollResultOutput);

        return getNextScreen(result);
    }

    private ConsoleViewType getNextScreen(InputProcessResult result){
        if(result == InputProcessResult.SUCCESS){
            return ConsoleViewType.MAIN_MENU;
        }else{
            return ConsoleViewType.UNENROLL_IN_EVENT;
        }
    }
}
