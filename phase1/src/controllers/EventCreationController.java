package controllers;

import UI.ConsoleView;
import entities.Event;
import presenters.EventCreationPresenter;
import useCaseClasses.EventManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class EventCreationController {

    private EventManager manager;
    private EventCreationPresenter presenter;

    public EventCreationController(EventManager manager, EventCreationPresenter presenter){
        this.manager = manager;
        this.presenter = presenter;
    }


    public ConsoleView.ConsoleViewType getNextScreen(String input){
        if(input.equals("back")) {
            return ConsoleView.ConsoleViewType.MAIN_MENU;
        }

        String[] parametersForEvent = input.split(",");

        UUID eventID = UUID.fromString(parametersForEvent[2]);

        UUID speakerID = UUID.fromString(parametersForEvent[3]);
        UUID organizerID = UUID.fromString(parametersForEvent[4]);

        LocalDateTime time = LocalDateTime.parse(parametersForEvent[1]);
        int roomNum = Integer.parseInt(parametersForEvent[5]);

        if (manager.availabilityInTime(time)){
            System.out.println("This time slot is full");
            return ConsoleView.ConsoleViewType.CREATE_EVENT;
        }

        Event eventCreated = new Event(parametersForEvent[0], time, eventID, organizerID, speakerID,
        new ArrayList<>(), roomNum);

        if (manager.addEvent(eventCreated)){
            presenter.setInputResponse("The event is added");
        }else{
            presenter.setInputResponse("The room is already booked. Try another room");
            return ConsoleView.ConsoleViewType.CREATE_EVENT;
        }

        return ConsoleView.ConsoleViewType.MAIN_MENU;
    }
}
