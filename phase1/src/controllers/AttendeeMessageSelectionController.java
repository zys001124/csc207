package controllers;

import useCaseClasses.UserManager;

public class AttendeeMessageSelectionController {

    private UserManager userManager;

    public AttendeeMessageSelectionController(UserManager am) {
        userManager = am;
    }

    // TODO signal to presenter(s) which screen to prepare
    public void getMessageScreen(String input) {

    }
}
