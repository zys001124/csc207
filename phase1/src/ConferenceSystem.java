
import UI.ConsoleView;
import UI.LoginView;
import UI.MenuInputView;
import controllers.LoginController;
import controllers.MenuInputController;
import entities.Event;
import entities.Message;
import entities.User;
import gateways.loaders.UserLoader;
import gateways.loaders.EventLoader;
import gateways.loaders.MessageLoader;
import presenters.LoginPresenter;
import presenters.MenuInputPresenter;
import useCaseClasses.UserManager;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ConferenceSystem {

    // global final variables
    public static final String ATTENDEE_CSV_PATH = "userData.csv";
    public static final String MESSAGE_CSV_PATH = "messageData.csv";
    public static final String EVENT_CSV_PATH = "eventData.csv";

    // Managers
    private UserManager userManager;
    private MessageManager messageManager;
    private EventManager eventManager;

    // Controllers
    private LoginController loginController;
    private MenuInputController menuInputController;
    private MenuInputPresenter menuInputPresenter;

    // Presenters
    private LoginPresenter loginPresenter;

    // Views
    private LoginView loginView;
    private MenuInputView menuInputView;

    // This will instantiate all relevant objects and then present the menu view
    public void run() {
        init();

        // Program loop
        Scanner in = new Scanner(System.in);
        ConsoleView view = loginView;
        while(true) {
            // UI prints string for whatever option the user is on
            ConsoleView.ConsoleViewType nextScreenType = view.runFlow(in);
            if(nextScreenType == null) { // If a null view is returned it means the user has exited the program;
                break;
            }

            view = getView(nextScreenType);
        }

        // TODO Should be serializing objects here and saving them
    }

    private void init() {
        initializeUseCases();

        initializeControllers();
        initializePresenters();

        initializeViews();
    }

    private void initializeUseCases() {
        UserLoader userLoader = new UserLoader();
        MessageLoader messageLoader = new MessageLoader();
        EventLoader eventLoader = new EventLoader();

        List<User> users;
        List<Message> messages ;
        List<Event> events;

        try {

            users = userLoader.loadAll(ATTENDEE_CSV_PATH);
            messages = messageLoader.loadAll(MESSAGE_CSV_PATH);
            events = eventLoader.loadAll(EVENT_CSV_PATH);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
            return;
        }

        userManager = new UserManager(users);
        messageManager = new MessageManager(messages);
        eventManager = new EventManager(events);
    }

    private void initializeControllers() {
        loginController = new LoginController(userManager);
        menuInputController = new MenuInputController(userManager);
    }

    private void initializePresenters() {
        loginPresenter = new LoginPresenter();
        menuInputPresenter = new MenuInputPresenter();
    }

    private void initializeViews() {
        loginView = new LoginView(loginController, loginPresenter);
        menuInputView = new MenuInputView(menuInputController, menuInputPresenter);
    }

    private ConsoleView getView(ConsoleView.ConsoleViewType type) {
        switch (type) {
            case LOGIN: return loginView;
            case MAIN_MENU: return menuInputView;
        }
        return null;
    }
}
