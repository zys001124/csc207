package controllers;

import UI.ConsoleView;
import useCaseClasses.AttendeeManager;

public class AttendeeMessageSelectionController {

    private AttendeeManager attendeeManager;

    public AttendeeMessageSelectionController(AttendeeManager am) {
        attendeeManager = am;
    }

    // TODO signal to presenter(s) which screen to prepare
    public void getMessageScreen(String input) {

    }
}
