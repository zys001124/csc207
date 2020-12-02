package handlers;

import presenters.*;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

public class PresenterHandler {

    // Presenters
    private LoginPresenter loginPresenter;
    private MenuInputPresenter menuInputPresenter;
    private MessageUserPresenter messageUserPresenter;
    private CreateAccountPresenter createAccountPresenter;
    private EventEnrollPresenter eventEnrollPresenter;
    private EventUnEnrollPresenter eventUnEnrollPresenter;
    private EventCancelPresenter eventCancelPresenter;
    private EventCreationPresenter eventCreationPresenter;
    private MessageAllAttendingEventPresenter messageAllAttendingEventPresenter;
    private MessageAllSpeakersPresenter messageAllSpeakersPresenter;
    private MessageAllAttendeesPresenter messageAllAttendeesPresenter;
    private SeeSchedulePresenter seeSchedulePresenter;
    private ChangeEventCapacityPresenter changeEventCapacityPresenter;
    private  DeleteAccountPresenter deleteAccountPresenter;
    private ViewMessagesPresenter viewMessagesPresenter;

    public PresenterHandler(UseCaseHandler useCaseHandler, ControllerHandler controllerHandler, SceneNavigator sceneNavigator) {
        constructProgramPresenters(useCaseHandler, controllerHandler, sceneNavigator);
    }

    public void constructProgramPresenters(UseCaseHandler useCaseHandler, ControllerHandler controllerHandler, SceneNavigator sceneNavigator) {

        UserManager userManager = useCaseHandler.getUserManager();
        MessageManager messageManager = useCaseHandler.getMessageManager();
        EventManager eventManager = useCaseHandler.getEventManager();

        //loginPresenter = new LoginPresenter(controllerHandler.getLoginController(), sceneNavigator);
        menuInputPresenter = new MenuInputPresenter(userManager);
        messageUserPresenter = new MessageUserPresenter(userManager, messageManager);
        createAccountPresenter = new CreateAccountPresenter();
        eventEnrollPresenter = new EventEnrollPresenter(eventManager);
        eventUnEnrollPresenter = new EventUnEnrollPresenter(eventManager, userManager);
        eventCancelPresenter = new EventCancelPresenter(eventManager);
        eventCreationPresenter = new EventCreationPresenter();
        messageAllAttendingEventPresenter = new MessageAllAttendingEventPresenter(userManager, eventManager);
        messageAllSpeakersPresenter = new MessageAllSpeakersPresenter();
        messageAllAttendeesPresenter = new MessageAllAttendeesPresenter();
        seeSchedulePresenter = new SeeSchedulePresenter(eventManager, userManager);
        changeEventCapacityPresenter = new ChangeEventCapacityPresenter();
        deleteAccountPresenter = new DeleteAccountPresenter(userManager);
        viewMessagesPresenter = new ViewMessagesPresenter(userManager, messageManager);
    }

    public LoginPresenter getLoginPresenter() {
        return loginPresenter;
    }

    public MenuInputPresenter getMenuInputPresenter() {
        return menuInputPresenter;
    }

    public MessageUserPresenter getMessageUserPresenter() {
        return messageUserPresenter;
    }

    public CreateAccountPresenter getCreateAccountPresenter() {
        return createAccountPresenter;
    }

    public EventEnrollPresenter getEventEnrollPresenter() {
        return eventEnrollPresenter;
    }

    public EventUnEnrollPresenter getEventUnEnrollPresenter() {
        return eventUnEnrollPresenter;
    }

    public EventCancelPresenter getEventCancelPresenter() {
        return eventCancelPresenter;
    }

    public EventCreationPresenter getEventCreationPresenter() {
        return eventCreationPresenter;
    }

    public DeleteAccountPresenter getDeleteAccountPresenter() {
        return deleteAccountPresenter;
    }

    public MessageAllAttendingEventPresenter getMessageAllAttendingEventPresenter() {
        return messageAllAttendingEventPresenter;
    }

    public MessageAllSpeakersPresenter getMessageAllSpeakersPresenter() {
        return messageAllSpeakersPresenter;
    }

    public MessageAllAttendeesPresenter getMessageAllAttendeesPresenter() {
        return messageAllAttendeesPresenter;
    }

    public SeeSchedulePresenter getSeeSchedulePresenter() {
        return seeSchedulePresenter;
    }

    public ChangeEventCapacityPresenter getChangeEventCapacityPresenter() {
        return changeEventCapacityPresenter;
    }

    public ViewMessagesPresenter getViewMessagesPresenter(){ return viewMessagesPresenter;}
}
