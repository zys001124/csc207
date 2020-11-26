import UI.*;
import controllers.*;
import entities.Event;
import entities.Message;
import entities.User;
import gateways.FirebaseGateway;
import presenters.*;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * A conference system for managing the creation/modification/deletion
 * of Users, Events, and Messages at a tech conference
 */
public class ConferenceSystem {

    // global final variables
    public static final String USER_DATA_PATH = "userData.txt";
    public static final String MESSAGE_DATA_PATH = "messageData.txt";
    public static final String EVENT_DATA_PATH = "eventData.txt";

    private UseCaseHandler useCaseHandler;

    private ControllerHandler controllerHandler;

    private PresenterHandler presenterHandler;

    private ViewHandler viewHandler;

    /**
     * Loads all entities, runs the program, saves all entities
     */

    public ConferenceSystem() {
        init();
    }

    public void run() {

        Scanner in = new Scanner(System.in);

        ConsoleView.ConsoleViewType nextScreenType = ConsoleView.ConsoleViewType.LOGIN;
        ConsoleView view = viewHandler.getView(nextScreenType);

        while (nextScreenType == ConsoleView.ConsoleViewType.LOGIN) {
            // UI prints string for whatever option the user is on
            nextScreenType = view.runFlow(in);
        }

        view = viewHandler.getMenuInputView();

        while (view != null) {
            // UI prints string for whatever option the user is on
            nextScreenType = view.runFlow(in);
            view = viewHandler.getView(nextScreenType);
        }

        useCaseHandler.saveEntities();
    }

    private void init() {
        useCaseHandler = new UseCaseHandler();
        controllerHandler = new ControllerHandler(useCaseHandler);
        presenterHandler = new PresenterHandler(useCaseHandler);

        viewHandler = new ViewHandler(controllerHandler, presenterHandler);
    }

}
