import UI.*;

public class ViewHandler {
    
    // Views
    private LoginView loginView;
    private MenuInputView menuInputView;
    private MessageUserView messageUserView;
    private CreateAccountView createAccountView;
    private EventEnrollView eventEnrollView;
    private EventUnEnrollView eventUnEnrollView;
    private EventCancelView eventCancelView;
    private EventCreationView eventCreationView;
    private MessageAllAttendingEventView messageAllAttendingEventView;
    private MessageAllSpeakersView messageAllSpeakersView;
    private MessageAllAttendeesView messageAllAttendeesView;
    private SeeScheduleView seeScheduleView;
    private NewEventCapacityView newEventCapacityView;
    private DeleteAccountView deleteAccountView;

    public ViewHandler(ControllerHandler ch, PresenterHandler ph) {
        constructProgramViews(ch, ph);
    }

    public void constructProgramViews(ControllerHandler ch, PresenterHandler ph) {
        loginView = new LoginView(ch.getLoginController(), ph.getLoginPresenter());
        menuInputView = new MenuInputView(ch.getMenuInputController(), ph.getMenuInputPresenter());
        messageUserView = new MessageUserView(ch.getMessageUserController(), ph.getMessageUserPresenter());
        createAccountView = new CreateAccountView(ch.getCreateAccountController(), ph.getCreateAccountPresenter());
        eventEnrollView = new EventEnrollView(ch.getEventEnrollController(), ph.getEventEnrollPresenter());
        eventUnEnrollView = new EventUnEnrollView(ch.getEventUnEnrollController(), ph.getEventUnEnrollPresenter());
        eventCancelView = new EventCancelView(ch.getEventCancelController(), ph.getEventCancelPresenter());
        eventCreationView = new EventCreationView(ch.getEventCreationController(), ph.getEventCreationPresenter());
        messageAllAttendingEventView = new MessageAllAttendingEventView(ch.getMessageAllAttendingEventController(), ph.getMessageAllAttendingEventPresenter());
        messageAllSpeakersView = new MessageAllSpeakersView(ch.getMessageAllSpeakersController(), ph.getMessageAllSpeakersPresenter());
        messageAllAttendeesView = new MessageAllAttendeesView(ch.getMessageAllAttendeesController(), ph.getMessageAllAttendeesPresenter());
        seeScheduleView = new SeeScheduleView(ph.getSeeSchedulePresenter());
        newEventCapacityView = new NewEventCapacityView(ch.getChangeCapacityController(),ph.getChangeEventCapacityPresenter());
        deleteAccountView = new DeleteAccountView(ch.getDeleteAccountController(), ph.getDeleteAccountPresenter());
    }

    public ConsoleView getView(ConsoleView.ConsoleViewType type) {
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
            case CREATE_ACCOUNT:
                return createAccountView;
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
            case DELETE_ACCOUNT:
                return deleteAccountView;
            case MESSAGE_ALL_ATTENDING_EVENT:
                return messageAllAttendingEventView;
            case MESSAGE_ALL_SPEAKERS:
                return messageAllSpeakersView;
            case MESSAGE_ALL_ATTENDEES:
                return messageAllAttendeesView;
            case CHANGE_CAPACITY:
                return newEventCapacityView;
            default:
                return null;
        }
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public MenuInputView getMenuInputView() {
        return menuInputView;
    }

    public MessageUserView getMessageUserView() {
        return messageUserView;
    }

    public CreateAccountView getCreateAccountView() {
        return createAccountView;
    }

    public EventEnrollView getEventEnrollView() {
        return eventEnrollView;
    }

    public DeleteAccountView getDeleteAccountView() {
        return deleteAccountView;
    }

    public EventUnEnrollView getEventUnEnrollView() {
        return eventUnEnrollView;
    }

    public EventCancelView getEventCancelView() {
        return eventCancelView;
    }

    public EventCreationView getEventCreationView() {
        return eventCreationView;
    }

    public MessageAllAttendingEventView getMessageAllAttendingEventView() {
        return messageAllAttendingEventView;
    }

    public MessageAllSpeakersView getMessageAllSpeakersView() {
        return messageAllSpeakersView;
    }

    public MessageAllAttendeesView getMessageAllAttendeesView() {
        return messageAllAttendeesView;
    }

    public SeeScheduleView getSeeScheduleView() {
        return seeScheduleView;
    }

    public NewEventCapacityView getNewEventCapacityView(){return newEventCapacityView;}

}
