package UI;

import java.util.Scanner;

public abstract class ConsoleView {

    public abstract ConsoleViewType runFlow(Scanner inputScanner);

    // If your name is next to something make sure it works
    public enum ConsoleViewType {
        LOGIN,
        MAIN_MENU,
        MESSAGE_USER, // Luka
        MESSAGE_ALL_ATTENDING_EVENT, // Jon - Finished
        MESSAGE_ALL_SPEAKERS, // Luka
        MESSAGE_ALL_ATTENDEES, // Luka
        EVENT_SCHEDULE, // Kelvin
        CREATE_EVENT, // Jay - Finished
        CANCEL_EVENT, // Yaosheng - Finished
        ENROLL_IN_EVENT, // Kelvin - Finished
        UNENROLL_IN_EVENT, // Kelvin - Finished
        CREATE_SPEAKER_ACCOUNT, // Lewis - Finished
    }
}
