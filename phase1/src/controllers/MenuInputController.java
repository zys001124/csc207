package controllers;

import UI.ConsoleView;
import useCaseClasses.AttendeeManager;
import useCaseClasses.MessageManager;

public class MenuInputController {

    private AttendeeManager attendeeManager;
    private MessageManager messageManager;

    public MenuInputController(AttendeeManager am, MessageManager mm) {
        attendeeManager = am;
        messageManager = mm;
    }

    public void getNextScreen(String input) {
        // TODO signal to presenter(s) which screen to prepare
    }
}
