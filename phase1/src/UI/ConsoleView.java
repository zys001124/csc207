package UI;

import java.util.Scanner;

public abstract class ConsoleView {

    public abstract ConsoleViewType runFlow(Scanner inputScanner);

    // If your name is next to something make sure it works
    public enum ConsoleViewType {
        LOGIN,
        MAIN_MENU,
        MESSAGE_USERS_MENU, // Luka
        MESSAGE_USER, // Luka
        EVENT_LIST_FOR_MESSAGING, // Jon
        MESSAGE_ALL_ATTENDING_EVENT, // Jon
        MESSAGE_ALL_SPEAKERS,
        MESSAGE_ALL_ATTENDEES,
        EVENT_SCHEDULE, // Kelvin
        CREATE_EVENT, // Jay
        CANCEL_EVENT, // Yaosheng
        ENROLL_IN_EVENT, // Kelvin or Simon?
        UNENROLL_IN_EVENT, // Kelvin or Simon?
        CREATE_SPEAKER_ACCOUNT, // Lewis - Finished

    }
}
