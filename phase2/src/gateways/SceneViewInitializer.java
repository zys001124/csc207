package gateways;

import controllers.Controller;
import controllers.LoginController;
import controllers.LoginListener;
import controllers.SceneNavigator;
import javafx.fxml.FXMLLoader;
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
 * Used to initialize the SceneNavigator class on program launch
 */
public class SceneViewInitializer {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private UserManager um;
    private MessageManager mm;
    private EventManager em;

    private SceneNavigator sceneNavigator;

    private String loginScenePath;
    private String speakerMenuInputScenePath;
    private String organizerMenuInputScenePath;
    private String attendeeMenuInputScenePath;
    private String vipMenuInputScenePath;
    private String adminMenuInputScenePath;
    private String messageUserScenePath;
    private String createAccountScenePath;
    private String eventEnrollScenePath;
    private String eventUnEnrollScenePath;
    private String eventCancelScenePath;
    private String eventCreationScenePath;
    private String messageAllAttendingEventScenePath;
    private String messageAllSpeakersScenePath;
    private String messageAllAttendeesScenePath;
    private String seeScheduleScenePath;
    private String changeEventCapacityScenePath;
    private String deleteAccountScenePath;

    /**
     * Creates a new SceneViewInitializer. Creates a new SceneNavigator in the process which allows for scene switching.
     * After this is called scenes in SceneNavigator are still not initialized yet, and need to be loaded with loadScenes()
     *
     * @param applicationStage the main javafx Stage (window) used in this program
     * @param um               the UserManager to be held by the controllers
     * @param mm               the MessageManager to be held by the controllers
     * @param em               the EventManager to be held by the controllers
     */
    public SceneViewInitializer(Stage applicationStage, UserManager um, MessageManager mm, EventManager em) {
        initializeFilePaths();
        this.um = um;
        this.mm = mm;
        this.em = em;

        this.sceneNavigator = new SceneNavigator(applicationStage);
    }

    /**
     * Setting up all file paths for the javafx scenes
     */
    private void initializeFilePaths() {
        loginScenePath = "loginScene.fxml";
        speakerMenuInputScenePath = "Speaker Main Menu.fxml";
        organizerMenuInputScenePath = "Organizer Main Menu.fxml";
        attendeeMenuInputScenePath = "Attendee Main Menu.fxml";
        vipMenuInputScenePath = "VIP Main Menu.fxml";
        adminMenuInputScenePath = "Admin Main Menu.fxml";
        messageUserScenePath = "Message User.fxml";
        createAccountScenePath = "Create User Account.fxml";
        eventEnrollScenePath = "Enroll Event.fxml";
        eventUnEnrollScenePath = "Unenroll Event.fxml";
        eventCancelScenePath = "Cancel Event.fxml";
        eventCreationScenePath = "Create Event.fxml";
        messageAllAttendingEventScenePath = "Message All Event Attendees.fxml";
        messageAllSpeakersScenePath = "Message All Speakers.fxml";
        messageAllAttendeesScenePath = "Message All Attendees.fxml";
        seeScheduleScenePath = "See Event Schedule.fxml";
        changeEventCapacityScenePath = "Change Event Capacity.fxml";
        deleteAccountScenePath = "Delete User Account.fxml";
    }

    /**
     * Loads up all javafx scenes for program startup and displays the login scene
     */
    public void loadScenes() {
        Scene loginScene = initializeLoginScene(loginScenePath, sceneNavigator, this::initializeSceneNavigator);
        sceneNavigator.setSceneView(SceneNavigator.SceneViewType.LOGIN, loginScene);

        sceneNavigator.switchSceneView(SceneNavigator.SceneViewType.LOGIN);
        sceneNavigator.getApplicationStage().show();
    }

    /**
     * Sets up all javafx Scenes used in this program's SceneNavigator based on file paths.
     */
    private void initializeSceneNavigator() {
        Scene speakerMenuInputScene = initializeScene(speakerMenuInputScenePath, sceneNavigator);
        sceneNavigator.setSceneView(SceneNavigator.SceneViewType.SPEAKER_MAIN_MENU, speakerMenuInputScene);

        Scene organizerMenuInputScene = initializeScene(organizerMenuInputScenePath, sceneNavigator);
        sceneNavigator.setSceneView(SceneNavigator.SceneViewType.ORGANIZER_MAIN_MENU, organizerMenuInputScene);

        Scene attendeeMenuInputScene = initializeScene(attendeeMenuInputScenePath, sceneNavigator);
        sceneNavigator.setSceneView(SceneNavigator.SceneViewType.ATTENDEE_MAIN_MENU, attendeeMenuInputScene);

        Scene vipMenuInputScene = initializeScene(vipMenuInputScenePath, sceneNavigator);
        sceneNavigator.setSceneView(SceneNavigator.SceneViewType.VIP_MAIN_MENU, vipMenuInputScene);

        Scene adminMenuInputScene = initializeScene(adminMenuInputScenePath, sceneNavigator);
        sceneNavigator.setSceneView(SceneNavigator.SceneViewType.ADMIN_MAIN_MENU, adminMenuInputScene);

        Scene messageUserScene = initializeScene(messageUserScenePath, sceneNavigator);
        sceneNavigator.setSceneView(SceneNavigator.SceneViewType.MESSAGE_USER, messageUserScene);

        Scene createAccountScene = initializeScene(createAccountScenePath, sceneNavigator);
        sceneNavigator.setSceneView(SceneNavigator.SceneViewType.CREATE_ACCOUNT, createAccountScene);

        Scene eventEnrollScene = initializeScene(eventEnrollScenePath, sceneNavigator);
        sceneNavigator.setSceneView(SceneNavigator.SceneViewType.ENROLL_IN_EVENT, eventEnrollScene);

        Scene eventUnEnrollScene = initializeScene(eventUnEnrollScenePath, sceneNavigator);
        sceneNavigator.setSceneView(SceneNavigator.SceneViewType.UNENROLL_IN_EVENT, eventUnEnrollScene);

        Scene eventCancelScene = initializeScene(eventCancelScenePath, sceneNavigator);
        sceneNavigator.setSceneView(SceneNavigator.SceneViewType.CANCEL_EVENT, eventCancelScene);

        Scene eventCreationScene = initializeScene(eventCreationScenePath, sceneNavigator);
        sceneNavigator.setSceneView(SceneNavigator.SceneViewType.CREATE_EVENT, eventCreationScene);

        Scene messageAllAttendingEventScene = initializeScene(messageAllAttendingEventScenePath, sceneNavigator);
        sceneNavigator.setSceneView(SceneNavigator.SceneViewType.MESSAGE_ALL_ATTENDING_EVENT, messageAllAttendingEventScene);

        Scene messageAllSpeakersScene = initializeScene(messageAllSpeakersScenePath, sceneNavigator);
        sceneNavigator.setSceneView(SceneNavigator.SceneViewType.MESSAGE_ALL_SPEAKERS, messageAllSpeakersScene);

        Scene messageAllAttendeesScene = initializeScene(messageAllAttendeesScenePath, sceneNavigator);
        sceneNavigator.setSceneView(SceneNavigator.SceneViewType.MESSAGE_ALL_ATTENDEES, messageAllAttendeesScene);

        Scene seeScheduleScene = initializeScene(seeScheduleScenePath, sceneNavigator);
        sceneNavigator.setSceneView(SceneNavigator.SceneViewType.EVENT_SCHEDULE, seeScheduleScene);

        Scene changeEventCapacityScene = initializeScene(changeEventCapacityScenePath, sceneNavigator);
        sceneNavigator.setSceneView(SceneNavigator.SceneViewType.CHANGE_CAPACITY, changeEventCapacityScene);

        Scene deleteAccountScene = initializeScene(deleteAccountScenePath, sceneNavigator);
        sceneNavigator.setSceneView(SceneNavigator.SceneViewType.DELETE_ACCOUNT, deleteAccountScene);
    }

    private Scene initializeScene(String fxmlPath, SceneNavigator sceneNavigator) {
        Scene scene;
        try {
            URL url = new File(fxmlPath).toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            scene = new Scene(loader.load());
            Controller controller = loader.getController();
            controller.setUserManager(um);
            controller.setMessageManager(mm);
            controller.setEventManager(em);
            controller.setSceneNavigator(sceneNavigator);
        } catch (IOException e) {
            scene = new Scene(new VBox(), WIDTH, HEIGHT);
        }
        return scene;
    }

    private Scene initializeLoginScene(String fxmlPath, SceneNavigator sceneNavigator, LoginListener listener) {
        Scene scene;
        try {
            URL url = new File(fxmlPath).toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            scene = new Scene(loader.load());
            LoginController controller = loader.getController();
            controller.setUserManager(um);
            controller.setMessageManager(mm);
            controller.setEventManager(em);
            controller.setSceneNavigator(sceneNavigator);
            controller.addLoginListener(listener);
        } catch (IOException e) {
            scene = new Scene(new VBox(), WIDTH, WIDTH);
        }
        return scene;
    }
}
