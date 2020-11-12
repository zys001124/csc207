package UI;

import java.util.Scanner;

public abstract class ConsoleView {

    public abstract ConsoleViewType runFlow(Scanner inputScanner);

    // If your name is next to something make sure it works
    public enum ConsoleViewType {
        LOGIN,
        MAIN_MENU,
        MESSAGE_USER, // Luka
        MESSAGE_ALL_ATTENDING_EVENT, // Jon
        MESSAGE_ALL_SPEAKERS,
        MESSAGE_ALL_ATTENDEES,
        EVENT_SCHEDULE, // Kelvin
        CREATE_EVENT, // Jay
        CANCEL_EVENT, // Yaosheng
        ENROLL_IN_EVENT, // Kelvin - Finished
        UNENROLL_IN_EVENT, // Kelvin
        CREATE_SPEAKER_ACCOUNT, // Lewis - Finished

    }
}
