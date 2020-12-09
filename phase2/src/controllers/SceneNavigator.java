package controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class which holds all possible scenes in the program.
 * Used to switch between scenes by the controllers on button clicks.
 */
public class SceneNavigator {

    private final Stage applicationStage;

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

    /**
     * Create a new SceneNavigator with the stage <applicationStage>
     */
    public SceneNavigator(Stage applicationStage) {
        this.applicationStage = applicationStage;
    }

    /**
     * Switch the current scene displayed in the application window to <sceneViewType>
     *
     * @param sceneViewType the scene to switch to
     */
    public void switchSceneView(SceneViewType sceneViewType) {
        applicationStage.setScene(getSceneView(sceneViewType));
    }

    /**
     * Sets the old scene represented by <sceneViewType> to a new scene
     *
     * @param sceneViewType the scene which needs to be set
     * @param scene         the new scene
     */
    public void setSceneView(SceneViewType sceneViewType, Scene scene) {
        switch (sceneViewType) {
            case LOGIN: {
                this.loginScene = scene;
                break;
            }
            case SPEAKER_MAIN_MENU: {
                this.speakerMenuInputScene = scene;
                break;
            }
            case ORGANIZER_MAIN_MENU: {
                this.organizerMenuInputScene = scene;
                break;
            }
            case ATTENDEE_MAIN_MENU: {
                this.attendeeMenuInputScene = scene;
                break;
            }
            case VIP_MAIN_MENU: {
                this.vipMenuInputScene = scene;
                break;
            }
            case ADMIN_MAIN_MENU: {
                this.adminMenuInputScene = scene;
                break;
            }
            case MESSAGE_USER: {
                this.messageUserScene = scene;
                break;
            }
            case MESSAGE_ALL_ATTENDING_EVENT: {
                this.messageAllAttendingEventScene = scene;
                break;
            }
            case MESSAGE_ALL_SPEAKERS: {
                this.messageAllSpeakersScene = scene;
                break;
            }
            case MESSAGE_ALL_ATTENDEES: {
                this.messageAllAttendeesScene = scene;
                break;
            }
            case EVENT_SCHEDULE: {
                this.seeScheduleScene = scene;
                break;
            }
            case CREATE_EVENT: {
                this.eventCreationScene = scene;
                break;
            }
            case CANCEL_EVENT: {
                this.eventCancelScene = scene;
                break;
            }
            case ENROLL_IN_EVENT: {
                this.eventEnrollScene = scene;
                break;
            }
            case UNENROLL_IN_EVENT: {
                this.eventUnEnrollScene = scene;
                break;
            }
            case CREATE_ACCOUNT: {
                this.createAccountScene = scene;
                break;
            }
            case DELETE_ACCOUNT: {
                this.deleteAccountScene = scene;
                break;
            }
            case CHANGE_CAPACITY: {
                this.changeEventCapacityScene = scene;
                break;
            }
        }
    }

    private Scene getSceneView(SceneViewType sceneViewType) {
        switch (sceneViewType) {
            case LOGIN: {
                return loginScene;
            }
            case SPEAKER_MAIN_MENU: {
                return speakerMenuInputScene;
            }
            case ORGANIZER_MAIN_MENU: {
                return organizerMenuInputScene;
            }
            case ATTENDEE_MAIN_MENU: {
                return attendeeMenuInputScene;
            }
            case VIP_MAIN_MENU: {
                return vipMenuInputScene;
            }
            case ADMIN_MAIN_MENU: {
                return adminMenuInputScene;
            }
            case MESSAGE_USER: {
                return messageUserScene;
            }
            case MESSAGE_ALL_ATTENDING_EVENT: {
                return messageAllAttendingEventScene;
            }
            case MESSAGE_ALL_SPEAKERS: {
                return messageAllSpeakersScene;
            }
            case MESSAGE_ALL_ATTENDEES: {
                return messageAllAttendeesScene;
            }
            case EVENT_SCHEDULE: {
                return seeScheduleScene;
            }
            case CREATE_EVENT: {
                return eventCreationScene;
            }
            case CANCEL_EVENT: {
                return eventCancelScene;
            }
            case ENROLL_IN_EVENT: {
                return eventEnrollScene;
            }
            case UNENROLL_IN_EVENT: {
                return eventUnEnrollScene;
            }
            case CREATE_ACCOUNT: {
                return createAccountScene;
            }
            case DELETE_ACCOUNT: {
                return deleteAccountScene;
            }
            case CHANGE_CAPACITY: {
                return changeEventCapacityScene;
            }
        }
        return loginScene;
    }

    /**
     * Returns the current stage (window). Used to call the show() method in order to display the window on program launch.
     *
     * @return the programs main stage
     */
    public void showGUI() {
        applicationStage.show();
    }

}
