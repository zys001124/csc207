import UI.NewEventCapacityView;
import controllers.*;
import entities.Event;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

public class ControllerHandler {
    // Controllers
    private  LoginController loginController;
    private MenuInputController menuInputController;
    private MessageUserController messageUserController;
    private CreateAccountController createAccountController;
    private EventEnrollController eventEnrollController;
    private EventUnEnrollController eventUnEnrollController;
    private EventCancelController eventCancelController;
    private EventCreationController eventCreationController;
    private MessageAllAttendingEventController messageAllAttendingEventController;
    private MessageAllSpeakersController messageAllSpeakersController;
    private MessageAllAttendeesController messageAllAttendeesController;
    private DeleteAccountController deleteAccountController;
    private ChangeCapacityController changeCapacityController;

    public ControllerHandler(UseCaseHandler useCaseHandler) {
        constructProgramControllers(useCaseHandler);
    }

    public void constructProgramControllers(UseCaseHandler useCaseHandler) {

        UserManager userManager = useCaseHandler.getUserManager();
        MessageManager messageManager = useCaseHandler.getMessageManager();
        EventManager eventManager = useCaseHandler.getEventManager();

        loginController = new LoginController(userManager);
        menuInputController = new MenuInputController(userManager);
        messageUserController = new MessageUserController(messageManager, userManager);
        createAccountController = new CreateAccountController(userManager);
        eventEnrollController = new EventEnrollController(eventManager, userManager);
        eventUnEnrollController = new EventUnEnrollController(eventManager, userManager);
        eventCancelController = new EventCancelController(eventManager, userManager);
        eventCreationController = new EventCreationController(eventManager, userManager);
        messageAllAttendingEventController = new MessageAllAttendingEventController(userManager, messageManager, eventManager);
        messageAllSpeakersController = new MessageAllSpeakersController(messageManager, userManager);
        messageAllAttendeesController = new MessageAllAttendeesController(messageManager, userManager);
        deleteAccountController = new DeleteAccountController(userManager);
        changeCapacityController = new ChangeCapacityController(eventManager);

    }


    public LoginController getLoginController() {
        return loginController;
    }

    public MenuInputController getMenuInputController() {
        return menuInputController;
    }

    public MessageUserController getMessageUserController() {
        return messageUserController;
    }

    public CreateAccountController getCreateAccountController() {
        return createAccountController;
    }

    public EventEnrollController getEventEnrollController() {
        return eventEnrollController;
    }

    public DeleteAccountController getDeleteAccountController() {
        return deleteAccountController;
    }

    public EventUnEnrollController getEventUnEnrollController() {
        return eventUnEnrollController;
    }

    public EventCancelController getEventCancelController() {
        return eventCancelController;
    }

    public EventCreationController getEventCreationController() {
        return eventCreationController;
    }

    public MessageAllAttendingEventController getMessageAllAttendingEventController() {
        return messageAllAttendingEventController;
    }

    public MessageAllSpeakersController getMessageAllSpeakersController() {
        return messageAllSpeakersController;
    }

    public MessageAllAttendeesController getMessageAllAttendeesController() {
        return messageAllAttendeesController;
    }

    public ChangeCapacityController getChangeCapacityController(){
        return changeCapacityController;
    }
}
