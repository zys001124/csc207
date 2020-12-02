package UI;

import java.util.Scanner;

/**
 * Outlines what a view in the console should be able to do
 */
public abstract class GuiView {

    /**
     * Makes the flow visible on the screen and collects input
     *
     * @param inputScanner the Scanner that can be used to collect
     *                     input from the user
     * @return a ConsoleViewType - this describes the next ConsoleView
     * that should be displayed on screen
     */
    public abstract SceneType runFlow(Scanner inputScanner);

//    public abstract void initializeSceneLayout();

    /**
     * Represents a possible view that could be displayed to any user
     */
    public enum SceneType {
        LOGIN, // Finished
        MAIN_MENU, // Finisehd
        MESSAGE_USER, // Luka
        MESSAGE_ALL_ATTENDING_EVENT, // Jon - Finished
        MESSAGE_ALL_SPEAKERS, // Luka
        MESSAGE_ALL_ATTENDEES, // Luka
        EVENT_SCHEDULE, // Kelvin - Finished
        CREATE_EVENT, // Jay - Finished
        CANCEL_EVENT, // Yaosheng - Finished
        ENROLL_IN_EVENT, // Kelvin - Finished
        UNENROLL_IN_EVENT, // Kelvin - Finished
        CREATE_ACCOUNT, // Lewis - Finished
        DELETE_ACCOUNT,
        CHANGE_CAPACITY,
        VIEW_MESSAGES,
    }
}
