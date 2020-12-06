package handlers;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class which holds all possible scenes in the
 */

public class SceneNavigator {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private final Stage applicationStage;
    private final UseCaseHandler useCaseHandler;

    private Scene loginScene;
    private Scene speakerMenuInputScene;
    private Scene organizerMenuInputScene;
    private Scene attendeeMenuInputScene;
    private Scene vipMenuInputScene;
    private Scene adminMenuInputScene;
    private Scene messageUserScene;
    private Scene createAccountScene;
    private Scene eventEnrollScene;
    private Scene eventUnEnrollScene;
    private Scene eventCancelScene;
    private Scene eventCreationScene;
    private Scene messageAllAttendingEventScene;
    private Scene messageAllSpeakersScene;
    private Scene messageAllAttendeesScene;
    private Scene seeScheduleScene;
    private Scene changeEventCapacityScene;
    private Scene deleteAccountScene;

    public SceneNavigator(Stage applicationStage, UseCaseHandler useCaseHandler) {
        this.applicationStage = applicationStage;
        this.useCaseHandler = useCaseHandler;
    }

    public void switchSceneView(SceneViewType sceneViewType) {
        switch (sceneViewType) {
            case LOGIN: {
                applicationStage.setScene(getLoginScene());
                break;
            }
            case SPEAKER_MAIN_MENU: {
                applicationStage.setScene(getSpeakerMenuInputScene());
                break;
            }
            case ORGANIZER_MAIN_MENU: {
                applicationStage.setScene(getOrganizerMenuInputScene());
                break;
            }
            case ATTENDEE_MAIN_MENU: {
                applicationStage.setScene(getAttendeeMenuInputScene());
                break;
            }
            case VIP_MAIN_MENU: {
                applicationStage.setScene(getVipMenuInputScene());
                break;
            }
            case ADMIN_MAIN_MENU: {
                applicationStage.setScene(getAdminMenuInputScene());
                break;
            }
            case MESSAGE_USER: {
                applicationStage.setScene(getMessageUserScene());
                break;
            }
            case MESSAGE_ALL_ATTENDING_EVENT: {
                applicationStage.setScene(getMessageAllAttendingEventScene());
                break;
            }
            case MESSAGE_ALL_SPEAKERS: {
                applicationStage.setScene(getMessageAllSpeakersScene());
                break;
            }
            case MESSAGE_ALL_ATTENDEES: {
                applicationStage.setScene(getMessageAllAttendeesScene());
                break;
            }
            case EVENT_SCHEDULE: {
                applicationStage.setScene(getSeeScheduleScene());
                break;
            }
            case CREATE_EVENT: {
                applicationStage.setScene(getEventCreationScene());
                break;
            }
            case CANCEL_EVENT: {
                applicationStage.setScene(getEventCancelScene());
                break;
            }
            case ENROLL_IN_EVENT: {
                applicationStage.setScene(getEventEnrollScene());
                break;
            }
            case UNENROLL_IN_EVENT: {
                applicationStage.setScene(getEventUnEnrollScene());
                break;
            }
            case CREATE_ACCOUNT: {
                applicationStage.setScene(getCreateAccountScene());
                break;
            }
            case DELETE_ACCOUNT: {
                applicationStage.setScene(getDeleteAccountScene());
                break;
            }
            case CHANGE_CAPACITY: {
                applicationStage.setScene(getChangeEventCapacityScene());
                break;
            }
        }
    }

    public Stage getApplicationStage() {
        return applicationStage;
    }

    public Scene getLoginScene() {
        return loginScene;
    }

    public void setLoginScene(Scene loginScene) {
        this.loginScene = loginScene;
    }

    public Scene getSpeakerMenuInputScene() {
        return speakerMenuInputScene;
    }

    public void setSpeakerMenuInputScene(Scene menuInputScene) {
        this.speakerMenuInputScene = menuInputScene;
    }

    public Scene getOrganizerMenuInputScene() {
        return organizerMenuInputScene;
    }

    public void setOrganizerMenuInputScene(Scene menuInputScene) {
        this.organizerMenuInputScene = menuInputScene;
    }

    public Scene getVipMenuInputScene() {
        return vipMenuInputScene;
    }

    public void setVipMenuInputScene(Scene menuInputScene) {
        this.vipMenuInputScene = menuInputScene;
    }

    public Scene getAdminMenuInputScene() {
        return adminMenuInputScene;
    }

    public void setAdminMenuInputScene(Scene menuInputScene) {
        this.adminMenuInputScene = menuInputScene;
    }

    public Scene getAttendeeMenuInputScene() {
        return attendeeMenuInputScene;
    }

    public void setAttendeeMenuInputScene(Scene menuInputScene) {
        this.attendeeMenuInputScene = menuInputScene;
    }

    public Scene getMessageUserScene() {
        return messageUserScene;
    }

    public void setMessageUserScene(Scene messageUserScene) {
        this.messageUserScene = messageUserScene;
    }

    public Scene getCreateAccountScene() {
        return createAccountScene;
    }

    public void setCreateAccountScene(Scene createAccountScene) {
        this.createAccountScene = createAccountScene;
    }

    public Scene getEventEnrollScene() {
        return eventEnrollScene;
    }

    public void setEventEnrollScene(Scene eventEnrollScene) {
        this.eventEnrollScene = eventEnrollScene;
    }

    public Scene getEventUnEnrollScene() {
        return eventUnEnrollScene;
    }

    public void setEventUnEnrollScene(Scene eventUnEnrollScene) {
        this.eventUnEnrollScene = eventUnEnrollScene;
    }

    public Scene getEventCancelScene() {
        return eventCancelScene;
    }

    public void setEventCancelScene(Scene eventCancelScene) {
        this.eventCancelScene = eventCancelScene;
    }

    public Scene getEventCreationScene() {
        return eventCreationScene;
    }

    public void setEventCreationScene(Scene eventCreationScene) {
        this.eventCreationScene = eventCreationScene;
    }

    public Scene getMessageAllAttendingEventScene() {
        return messageAllAttendingEventScene;
    }

    public void setMessageAllAttendingEventScene(Scene messageAllAttendingEventScene) {
        this.messageAllAttendingEventScene = messageAllAttendingEventScene;
    }

    public Scene getMessageAllSpeakersScene() {
        return messageAllSpeakersScene;
    }

    public void setMessageAllSpeakersScene(Scene messageAllSpeakersScene) {
        this.messageAllSpeakersScene = messageAllSpeakersScene;
    }

    public Scene getMessageAllAttendeesScene() {
        return messageAllAttendeesScene;
    }

    public void setMessageAllAttendeesScene(Scene messageAllAttendeesScene) {
        this.messageAllAttendeesScene = messageAllAttendeesScene;
    }

    public Scene getSeeScheduleScene() {
        return seeScheduleScene;
    }

    public void setSeeScheduleScene(Scene seeScheduleScene) {
        this.seeScheduleScene = seeScheduleScene;
    }

    public Scene getChangeEventCapacityScene() {
        return changeEventCapacityScene;
    }

    public void setChangeEventCapacityScene(Scene changeEventCapacityScene) {
        this.changeEventCapacityScene = changeEventCapacityScene;
    }

    public Scene getDeleteAccountScene() {
        return deleteAccountScene;
    }

    public void setDeleteAccountScene(Scene deleteAccountScene) {
        this.deleteAccountScene = deleteAccountScene;
    }



    public enum SceneViewType {
        LOGIN, // Finished
        SPEAKER_MAIN_MENU, // Finished
        ORGANIZER_MAIN_MENU, //Finished
        ATTENDEE_MAIN_MENU, //Finished
        ADMIN_MAIN_MENU, //Finished
        VIP_MAIN_MENU, //Finished
        MESSAGE_USER, //Finished
        MESSAGE_ALL_ATTENDING_EVENT, //Finished
        MESSAGE_ALL_SPEAKERS, //Finished - SMALL BUG
        MESSAGE_ALL_ATTENDEES, //Finished - SMALL BUG
        EVENT_SCHEDULE, //Finished
        CREATE_EVENT, //Finished
        CANCEL_EVENT, //Finished
        ENROLL_IN_EVENT, // Finished
        UNENROLL_IN_EVENT, // Finished
        CREATE_ACCOUNT, //Finished
        DELETE_ACCOUNT, //Finished
        CHANGE_CAPACITY, //Finished
    }
}
