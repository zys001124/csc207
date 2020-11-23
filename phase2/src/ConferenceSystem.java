import UI.*;
import controllers.*;
import entities.Event;
import entities.Message;
import entities.User;
import gateways.loaders.EventLoader;
import gateways.loaders.MessageLoader;
import gateways.loaders.UserLoader;
import gateways.savers.EventSaver;
import gateways.savers.MessageSaver;
import gateways.savers.UserSaver;
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

    // Managers
    private UserManager userManager;
    private MessageManager messageManager;
    private EventManager eventManager;

    // Controllers
    private LoginController loginController;
    private MenuInputController menuInputController;
    private MessageUserController messageUserController;
    private CreateSpeakerAccountController createSpeakerAccountController;
    private EventEnrollController eventEnrollController;
    private EventUnEnrollController eventUnEnrollController;
    private EventCancelController eventCancelController;
    private EventCreationController eventCreationController;
    private MessageAllAttendingEventController messageAllAttendingEventController;
    private MessageAllSpeakersController messageAllSpeakersController;
    private MessageAllAttendeesController messageAllAttendeesController;

    // Presenters
    private LoginPresenter loginPresenter;
    private MenuInputPresenter menuInputPresenter;
    private MessageUserPresenter messageUserPresenter;
    private CreateSpeakerAccountPresenter createSpeakerAccountPresenter;
    private EventEnrollPresenter eventEnrollPresenter;
    private EventUnEnrollPresenter eventUnEnrollPresenter;
    private EventCancelPresenter eventCancelPresenter;
    private EventCreationPresenter eventCreationPresenter;
    private MessageAllAttendingEventPresenter messageAllAttendingEventPresenter;
    private MessageAllSpeakersPresenter messageAllSpeakersPresenter;
    private MessageAllAttendeesPresenter messageAllAttendeesPresenter;
    private SeeSchedulePresenter seeSchedulePresenter;

    // Views
    private LoginView loginView;
    private MenuInputView menuInputView;
    private MessageUserView messageUserView;
    private CreateSpeakerAccountView createSpeakerAccountView;
    private EventEnrollView eventEnrollView;
    private EventUnEnrollView eventUnEnrollView;
    private EventCancelView eventCancelView;
    private EventCreationView eventCreationView;
    private MessageAllAttendingEventView messageAllAttendingEventView;
    private MessageAllSpeakersView messageAllSpeakersView;
    private MessageAllAttendeesView messageAllAttendeesView;
    private SeeScheduleView seeScheduleView;

    /**
     * Loads all entiteis, runs the program, saves all entities
     */
    public void run() {
        init();
        loginView = new LoginView(loginController, loginPresenter);
        // Program loop
        Scanner in = new Scanner(System.in);

        ConsoleView.ConsoleViewType nextScreenType = ConsoleView.ConsoleViewType.LOGIN;
        ConsoleView view = getView(nextScreenType);

        while (nextScreenType == ConsoleView.ConsoleViewType.LOGIN) {
            // UI prints string for whatever option the user is on
            nextScreenType = view.runFlow(in);
        }

        // This depends on the type of user logged in
        initializeViews();
        view = menuInputView;

        while (view != null) {
            // UI prints string for whatever option the user is on
            nextScreenType = view.runFlow(in);
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
        List<Message> messages;
        List<Event> events;

        try {

            users = userLoader.loadAll(USER_DATA_PATH);
            messages = messageLoader.loadAll(MESSAGE_DATA_PATH);
            events = eventLoader.loadAll(EVENT_DATA_PATH);

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
            UserSaver userSaver = new UserSaver(USER_DATA_PATH);
            MessageSaver messageSaver = new MessageSaver(MESSAGE_DATA_PATH);
            EventSaver eventSaver = new EventSaver(EVENT_DATA_PATH);

            userSaver.saveAll(userManager.getUsers());
            messageSaver.saveAll(messageManager.getMessages());
            eventSaver.saveAll(eventManager.getEvents());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void initializeControllers() {
        loginController = new LoginController(userManager);
        menuInputController = new MenuInputController(userManager);
        messageUserController = new MessageUserController(messageManager, userManager);
        createSpeakerAccountController = new CreateSpeakerAccountController(userManager);
        eventEnrollController = new EventEnrollController(eventManager, userManager);
        eventUnEnrollController = new EventUnEnrollController(eventManager, userManager);
        eventCancelController = new EventCancelController(eventManager, eventCancelPresenter, userManager);
        eventCreationController = new EventCreationController(eventManager, userManager);
        messageAllAttendingEventController = new MessageAllAttendingEventController(userManager, messageManager, eventManager);
        messageAllSpeakersController = new MessageAllSpeakersController(messageManager, userManager);
        messageAllAttendeesController = new MessageAllAttendeesController(messageManager, userManager);

    }

    private void initializePresenters() {
        loginPresenter = new LoginPresenter();
        menuInputPresenter = new MenuInputPresenter(userManager);
        messageUserPresenter = new MessageUserPresenter(userManager, messageManager);
        createSpeakerAccountPresenter = new CreateSpeakerAccountPresenter();
        eventEnrollPresenter = new EventEnrollPresenter(eventManager);
        eventUnEnrollPresenter = new EventUnEnrollPresenter(eventManager, userManager);
        eventCancelPresenter = new EventCancelPresenter(eventManager);
        eventCreationPresenter = new EventCreationPresenter();
        messageAllAttendingEventPresenter = new MessageAllAttendingEventPresenter(userManager, eventManager);
        messageAllSpeakersPresenter = new MessageAllSpeakersPresenter();
        messageAllAttendeesPresenter = new MessageAllAttendeesPresenter();
        seeSchedulePresenter = new SeeSchedulePresenter(eventManager, userManager);
    }

    private void initializeViews() {
        menuInputView = new MenuInputView(menuInputController, menuInputPresenter);
        messageUserView = new MessageUserView(messageUserController, messageUserPresenter);
        createSpeakerAccountView = new CreateSpeakerAccountView(createSpeakerAccountController, createSpeakerAccountPresenter);
        eventEnrollView = new EventEnrollView(eventEnrollController, eventEnrollPresenter);
        eventUnEnrollView = new EventUnEnrollView(eventUnEnrollController, eventUnEnrollPresenter);
        eventCancelView = new EventCancelView(eventCancelController, eventCancelPresenter);
        eventCreationView = new EventCreationView(eventCreationController, eventCreationPresenter);
        messageAllAttendingEventView = new MessageAllAttendingEventView(messageAllAttendingEventController, messageAllAttendingEventPresenter);
        messageAllSpeakersView = new MessageAllSpeakersView(messageAllSpeakersController, messageAllSpeakersPresenter);
        messageAllAttendeesView = new MessageAllAttendeesView(messageAllAttendeesController, messageAllAttendeesPresenter);
        seeScheduleView = new SeeScheduleView(seeSchedulePresenter);
    }

    private ConsoleView getView(ConsoleView.ConsoleViewType type) {
        if (type == null) {
            return null;
        }
        switch (type) {
            case LOGIN:
                return loginView;
            case MAIN_MENU:
                return menuInputView;
            case MESSAGE_USER:
                return messageUserView;
            case CREATE_SPEAKER_ACCOUNT:
                return createSpeakerAccountView;
            case ENROLL_IN_EVENT:
                return eventEnrollView;
            case UNENROLL_IN_EVENT:
                return eventUnEnrollView;
            case EVENT_SCHEDULE:
                return seeScheduleView;
            case CANCEL_EVENT:
                return eventCancelView;
            case CREATE_EVENT:
                return eventCreationView;
            case MESSAGE_ALL_ATTENDING_EVENT:
                return messageAllAttendingEventView;
            case MESSAGE_ALL_SPEAKERS:
                return messageAllSpeakersView;
            case MESSAGE_ALL_ATTENDEES:
                return messageAllAttendeesView;
            default:
                return null;
        }
    }
}
