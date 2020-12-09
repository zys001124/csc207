package gateways;

import controllers.*;
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

    private final UserManager um;
    private final MessageManager mm;
    private final EventManager em;

    private final SceneNavigator sceneNavigator;

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
        SceneViewType.LOGIN.setFxmlPath("loginScene.fxml");
        SceneViewType.SPEAKER_MAIN_MENU.setFxmlPath("Speaker Main Menu.fxml");
        SceneViewType.ORGANIZER_MAIN_MENU.setFxmlPath("Organizer Main Menu.fxml");
        SceneViewType.ATTENDEE_MAIN_MENU.setFxmlPath("Attendee Main Menu.fxml");
        SceneViewType.VIP_MAIN_MENU.setFxmlPath("VIP Main Menu.fxml");
        SceneViewType.ADMIN_MAIN_MENU.setFxmlPath("Admin Main Menu.fxml");
        SceneViewType.MESSAGE_USER.setFxmlPath("Message User.fxml");
        SceneViewType.CREATE_ACCOUNT.setFxmlPath("Create User Account.fxml");
        SceneViewType.ENROLL_IN_EVENT.setFxmlPath("Enroll Event.fxml");
        SceneViewType.UNENROLL_IN_EVENT.setFxmlPath("Unenroll Event.fxml");
        SceneViewType.CANCEL_EVENT.setFxmlPath("Cancel Event.fxml");
        SceneViewType.CREATE_EVENT.setFxmlPath("Create Event.fxml");
        SceneViewType.MESSAGE_ALL_ATTENDING_EVENT.setFxmlPath("Message All Event Attendees.fxml");
        SceneViewType.MESSAGE_ALL_SPEAKERS.setFxmlPath("Message All Speakers.fxml");
        SceneViewType.MESSAGE_ALL_ATTENDEES.setFxmlPath("Message All Attendees.fxml");
        SceneViewType.EVENT_SCHEDULE.setFxmlPath("See Event Schedule.fxml");
        SceneViewType.CHANGE_CAPACITY.setFxmlPath("Change Event Capacity.fxml");
        SceneViewType.DELETE_ACCOUNT.setFxmlPath("Delete User Account.fxml");
    }

    /**
     * Loads up all javafx scenes for program startup and displays the login scene
     */
    public SceneNavigator getSceneNavigator() {
        prepareSceneNavigatorForStartup();
        return sceneNavigator;
    }

    private void prepareSceneNavigatorForStartup() {
        Scene loginScene = initializeLoginScene(SceneViewType.LOGIN.getFxmlPath(), sceneNavigator, this::initializeNonLoginScenes);
        sceneNavigator.setSceneView(SceneViewType.LOGIN, loginScene);
        sceneNavigator.switchSceneView(SceneViewType.LOGIN);
    }

    /**
     * Sets up all javafx Scenes used in this program's SceneNavigator based on file paths.
     */
    private void initializeNonLoginScenes() {

        // Loops through all SceneViewType's (besides login scene)
        // and initializes the scene, adds it to sceneNavigator
        for (SceneViewType viewType : SceneViewType.values()) {
            if (!viewType.equals(SceneViewType.LOGIN)) {
                initializeAndLoadSceneIntoNavigator(viewType, sceneNavigator);
            }
        }
    }

    private void initializeAndLoadSceneIntoNavigator(SceneViewType viewType, SceneNavigator sceneNavigator) {
        Scene scene;
        try {
            URL url = new File(viewType.getFxmlPath()).toURI().toURL();
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
        sceneNavigator.setSceneView(viewType, scene);
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
            scene = new Scene(new VBox(), WIDTH, HEIGHT);
        }
        return scene;
    }
}
