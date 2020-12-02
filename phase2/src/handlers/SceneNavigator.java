package handlers;

import controllers.Controller;
import controllers.LoginController;
import entities.Message;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Class which holds all possible scenes in the
 */

public class SceneNavigator {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private Stage applicationStage;
    private UseCaseHandler useCaseHandler;

    private Scene loginScene;
    private Scene menuInputScene;
    private Scene messageUserScene;
    private Scene createAccountScene;
    private Scene eventUnEnrollScene;
    private Scene eventCancelScene;
    private Scene eventCreationScene;
    private Scene eventEnrollScene;
    private Scene messageAllAttendingEventScene;
    private Scene messageAllSpeakersScene;
    private Scene messageAllAttendeesScene;
    private Scene seeScheduleScene;
    private Scene changeEventCapacityScene;
    private Scene deleteAccountScene;
    private Scene viewMessagesScene;

    public SceneNavigator(Stage applicationStage, UseCaseHandler useCaseHandler){
        this.applicationStage = applicationStage;
        this.useCaseHandler = useCaseHandler;
    }

    public void switchSceneView(SceneView sceneView){
        switch(sceneView) {
            case LOGIN: {
                applicationStage.setScene(getLoginScene());
                break;
            }
            case MAIN_MENU: {
                applicationStage.setScene(getMenuInputScene());
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
            case VIEW_MESSAGES: {
                applicationStage.setScene(getViewMessagesScene());
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

    public Scene getMenuInputScene() {
        return menuInputScene;
    }

    public Scene getMessageUserScene() {
        return messageUserScene;
    }

    public Scene getCreateAccountScene() {
        return createAccountScene;
    }

    public Scene getEventUnEnrollScene() {
        return eventUnEnrollScene;
    }

    public Scene getEventCancelScene() {
        return eventCancelScene;
    }

    public Scene getEventCreationScene() {
        return eventCreationScene;
    }

    public Scene getEventEnrollScene() {
        return eventEnrollScene;
    }

    public Scene getMessageAllAttendingEventScene() {
        return messageAllAttendingEventScene;
    }

    public Scene getMessageAllSpeakersScene() {
        return messageAllSpeakersScene;
    }

    public Scene getMessageAllAttendeesScene() {
        return messageAllAttendeesScene;
    }

    public Scene getSeeScheduleScene() {
        return seeScheduleScene;
    }

    public Scene getChangeEventCapacityScene() {
        return changeEventCapacityScene;
    }

    public Scene getDeleteAccountScene() {
        return deleteAccountScene;
    }

    public Scene getViewMessagesScene() {
        return viewMessagesScene;
    }

    public void setLoginScene(Scene loginScene) {
        this.loginScene = loginScene;
    }

    public void setMenuInputScene(Scene menuInputScene) {
        this.menuInputScene = menuInputScene;
    }

    public void setMessageUserScene(Scene messageUserScene) {
        this.messageUserScene = messageUserScene;
    }

    public void setCreateAccountScene(Scene createAccountScene) {
        this.createAccountScene = createAccountScene;
    }

    public void setEventUnEnrollScene(Scene eventUnEnrollScene) {
        this.eventUnEnrollScene = eventUnEnrollScene;
    }

    public void setEventCancelScene(Scene eventCancelScene) {
        this.eventCancelScene = eventCancelScene;
    }

    public void setEventCreationScene(Scene eventCreationScene) {
        this.eventCreationScene = eventCreationScene;
    }

    public void setEventEnrollScene(Scene eventEnrollScene) {
        this.eventEnrollScene = eventEnrollScene;
    }

    public void setMessageAllAttendingEventScene(Scene messageAllAttendingEventScene) {
        this.messageAllAttendingEventScene = messageAllAttendingEventScene;
    }

    public void setMessageAllSpeakersScene(Scene messageAllSpeakersScene) {
        this.messageAllSpeakersScene = messageAllSpeakersScene;
    }

    public void setMessageAllAttendeesScene(Scene messageAllAttendeesScene) {
        this.messageAllAttendeesScene = messageAllAttendeesScene;
    }

    public void setSeeScheduleScene(Scene seeScheduleScene) {
        this.seeScheduleScene = seeScheduleScene;
    }

    public void setChangeEventCapacityScene(Scene changeEventCapacityScene) {
        this.changeEventCapacityScene = changeEventCapacityScene;
    }

    public void setDeleteAccountScene(Scene deleteAccountScene) {
        this.deleteAccountScene = deleteAccountScene;
    }

    public void setViewMessagesScene(Scene viewMessagesScene) {
        this.viewMessagesScene = viewMessagesScene;
    }

    public enum SceneView {
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
