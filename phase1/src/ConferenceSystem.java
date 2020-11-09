
import UI.ConsoleView;
import UI.CreateSpeakerAccountView;
import UI.LoginView;
import UI.MenuInputView;
import controllers.CreateSpeakerAccountController;
import controllers.LoginController;
import controllers.MenuInputController;
import entities.Event;
import entities.Message;
import entities.User;
import gateways.loaders.UserLoader;
import gateways.loaders.EventLoader;
import gateways.loaders.MessageLoader;
import gateways.savers.EventSaver;
import gateways.savers.MessageSaver;
import gateways.savers.UserSaver;
import presenters.CreateSpeakerAccountPresenter;
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
    public static final String USER_CSV_PATH = "userData.csv";
    public static final String MESSAGE_CSV_PATH = "messageData.csv";
    public static final String EVENT_CSV_PATH = "eventData.csv";

    // Managers
    private UserManager userManager;
    private MessageManager messageManager;
    private EventManager eventManager;

    // Controllers
    private LoginController loginController;
    private MenuInputController menuInputController;
    private CreateSpeakerAccountController createSpeakerAccountController;

    // Presenters
    private LoginPresenter loginPresenter;
    private MenuInputPresenter menuInputPresenter;
    private CreateSpeakerAccountPresenter createSpeakerAccountPresenter;

    // Views
    private LoginView loginView;
    private MenuInputView menuInputView;
    private CreateSpeakerAccountView createSpeakerAccountView;

    // This will instantiate all relevant objects and then present the menu view
    public void run() {
        init();
        loginView = new LoginView(loginController, loginPresenter);
        // Program loop
        Scanner in = new Scanner(System.in);
        ConsoleView view = loginView;
        while(true) {
            // UI prints string for whatever option the user is on
            ConsoleView.ConsoleViewType nextScreenType = view.runFlow(in);
            if(nextScreenType != ConsoleView.ConsoleViewType.LOGIN) { // If a null view is returned it means the user has exited the program;
                break;
            }
        }

        // This depends on the type of user logged in
        initializeViews();
        view = menuInputView;

        while(true) {
            // UI prints string for whatever option the user is on
            ConsoleView.ConsoleViewType nextScreenType = view.runFlow(in);
            if(nextScreenType == null) {
                break;
            }
            view = getView(nextScreenType);
        }

        saveEntities();

    }

    private void init() {
        initializeUseCases();
        initializePresenters();
        initializeControllers();
    }

    private void initializeUseCases() {
        UserLoader userLoader = new UserLoader();
        MessageLoader messageLoader = new MessageLoader();
        EventLoader eventLoader = new EventLoader();

        List<User> users;
        List<Message> messages ;
        List<Event> events;

        try {

            users = userLoader.loadAll(USER_CSV_PATH);
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

    private void saveEntities() {
        try {
            UserSaver userSaver = new UserSaver(USER_CSV_PATH);
            MessageSaver messageSaver = new MessageSaver(MESSAGE_CSV_PATH);
            EventSaver eventSaver = new EventSaver(EVENT_CSV_PATH);

            userSaver.saveAll(userManager.getusers());
            messageSaver.saveAll(messageManager.getMessages());
            eventSaver.saveAll(eventManager.getEvents());
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void initializeControllers() {
        loginController = new LoginController(userManager);
        menuInputController = new MenuInputController(userManager);
        createSpeakerAccountController = new CreateSpeakerAccountController(userManager,
                createSpeakerAccountPresenter);

    }

    private void initializePresenters() {
        loginPresenter = new LoginPresenter();
        menuInputPresenter = new MenuInputPresenter();
        createSpeakerAccountPresenter = new CreateSpeakerAccountPresenter();
    }

    private void initializeViews() {
        menuInputView = new MenuInputView(menuInputController, menuInputPresenter, userManager.getCurrentlyLoggedIn().getType());
        createSpeakerAccountView = new CreateSpeakerAccountView(createSpeakerAccountController, createSpeakerAccountPresenter);
    }

    private ConsoleView getView(ConsoleView.ConsoleViewType type) {
        switch (type) {
            case LOGIN: return loginView;
            case MAIN_MENU: return menuInputView;
            case CREATE_SPEAKER_ACCOUNT: return createSpeakerAccountView;
        }
        return null;
    }
}
