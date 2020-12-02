import controllers.Controller;
import handlers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * A conference system for managing the creation/modification/deletion
 * of Users, Events, and Messages at a tech conference
 */
public class ConferenceSystem {

    // global final variables
    public static final String USER_DATA_PATH = "userData.txt";
    public static final String MESSAGE_DATA_PATH = "messageData.txt";
    public static final String EVENT_DATA_PATH = "eventData.txt";

    private UseCaseHandler useCaseHandler;
    private ControllerHandler controllerHandler;
    private PresenterHandler presenterHandler;

    private ViewHandler viewHandler;
    private SceneNavigator sceneNavigator;

    /**
     * Loads all entities, runs the program, saves all entities
     */

    public ConferenceSystem(Stage primaryStage) {
        initializeHandlers(primaryStage);
    }

    public void run() {
        initializeScenes();
        sceneNavigator.getApplicationStage().show();
    }

    public void initializeHandlers(Stage primaryStage) {
        useCaseHandler = new UseCaseHandler();
        controllerHandler = new ControllerHandler(useCaseHandler);

        sceneNavigator = new SceneNavigator(primaryStage, useCaseHandler);
        presenterHandler = new PresenterHandler(useCaseHandler, controllerHandler, sceneNavigator);
        viewHandler = new ViewHandler(controllerHandler, presenterHandler, sceneNavigator);
       // sceneNavigator.getApplicationStage().setScene(sceneNavigator.getLoginScene());
    }

    private void initializeScenes() {

        Scene loginScene = initializeScene("loginScene.fxml", sceneNavigator);
        sceneNavigator.setLoginScene(loginScene);

        Scene menuInputScene = initializeScene("test.fxml", sceneNavigator);
        sceneNavigator.setMenuInputScene(menuInputScene);

        Scene messageUserScene = initializeScene("loginScene.fxml", sceneNavigator);
        sceneNavigator.setMessageUserScene(messageUserScene);

        Scene createAccountScene = initializeScene("loginScene.fxml", sceneNavigator);
        sceneNavigator.setCreateAccountScene(createAccountScene);

        Scene eventUnEnrollScene = initializeScene("loginScene.fxml", sceneNavigator);
        sceneNavigator.setEventEnrollScene(eventUnEnrollScene);

        Scene eventCancelScene = initializeScene("loginScene.fxml", sceneNavigator);
        sceneNavigator.setEventCancelScene(eventCancelScene);

        Scene eventCreationScene = initializeScene("loginScene.fxml", sceneNavigator);
        sceneNavigator.setEventCreationScene(eventCreationScene);

        Scene eventEnrollScene = initializeScene("loginScene.fxml", sceneNavigator);
        sceneNavigator.setEventEnrollScene(eventEnrollScene);

        Scene messageAllAttendingEventScene = initializeScene("loginScene.fxml", sceneNavigator);
        sceneNavigator.setMessageAllAttendingEventScene(messageAllAttendingEventScene);

        Scene messageAllSpeakersScene = initializeScene("loginScene.fxml", sceneNavigator);
        sceneNavigator.setMessageAllSpeakersScene(messageAllSpeakersScene);

        Scene messageAllAttendeesScene = initializeScene("loginScene.fxml", sceneNavigator);
        sceneNavigator.setMessageAllAttendeesScene(messageAllAttendeesScene);

        Scene seeScheduleScene = initializeScene("loginScene.fxml", sceneNavigator);
        sceneNavigator.setSeeScheduleScene(seeScheduleScene);

        Scene changeEventCapacityScene = initializeScene("loginScene.fxml", sceneNavigator);
        sceneNavigator.setChangeEventCapacityScene(changeEventCapacityScene);

        Scene deleteAccountScene = initializeScene("loginScene.fxml", sceneNavigator);
        sceneNavigator.setDeleteAccountScene(deleteAccountScene);

        Scene viewMessagesScene = initializeScene("loginScene.fxml", sceneNavigator);
        sceneNavigator.setViewMessagesScene(viewMessagesScene);

        // Deafult to login scene
        sceneNavigator.switchSceneView(SceneNavigator.SceneView.LOGIN);
    }

    private Scene initializeScene(String fxmlPath, SceneNavigator sceneNavigator) {
        Scene scene;
        try{
            URL url = new File(fxmlPath).toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            scene = new Scene(loader.load());
            Controller controller = loader.getController();
            controller.setUserManager(useCaseHandler.getUserManager());
            controller.setMessageManager(useCaseHandler.getMessageManager());
            controller.setEventManager(useCaseHandler.getEventManager());
            controller.setSceneNavigator(sceneNavigator);
        } catch (IOException e) {
            scene = new Scene(new VBox(), 800, 600);
        }
        return scene;
    }
}
