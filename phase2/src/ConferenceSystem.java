import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import controllers.Controller;
import controllers.LoginController;
import controllers.LoginListener;
import handlers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
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
    private SceneNavigator sceneNavigator;

    /**
     * Loads all entities, runs the program, saves all entities
     */

    public ConferenceSystem(Stage primaryStage) {
        initializeFirebase();
        initializeSceneNavigator(primaryStage);
    }

    public void run() {

        Scene loginScene = initializeLoginScene("loginScene.fxml", sceneNavigator,
                this::initializeScenes);
        sceneNavigator.setLoginScene(loginScene);

        // Default to login scene
        sceneNavigator.switchSceneView(SceneNavigator.SceneViewType.LOGIN);
        sceneNavigator.getApplicationStage().show();
    }

    public void initializeFirebase() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("conference-system-key.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://conference-system-b48bf.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException exception) {
            exception.printStackTrace();
            System.exit(-1);
        }
    }

    public void initializeSceneNavigator(Stage primaryStage) {
        useCaseHandler = new UseCaseHandler();
        sceneNavigator = new SceneNavigator(primaryStage, useCaseHandler);
    }

    private void initializeScenes() {

        Scene speakerMenuInputScene = initializeScene("Speaker Main Menu.fxml", sceneNavigator);
        sceneNavigator.setSpeakerMenuInputScene(speakerMenuInputScene);

        Scene organizerMenuInputScene = initializeScene("Organizer Main Menu.fxml", sceneNavigator);
        sceneNavigator.setOrganizerMenuInputScene(organizerMenuInputScene);

        Scene attendeeMenuInputScene = initializeScene("Attendee Main Menu.fxml", sceneNavigator);
        sceneNavigator.setAttendeeMenuInputScene(attendeeMenuInputScene);

        Scene vipMenuInputScene = initializeScene("VIP Main Menu.fxml", sceneNavigator);
        sceneNavigator.setVipMenuInputScene(vipMenuInputScene);

        Scene adminMenuInputScene = initializeScene("Admin Main Menu.fxml", sceneNavigator);
        sceneNavigator.setAdminMenuInputScene(adminMenuInputScene);

        Scene messageUserScene = initializeScene("Message User.fxml", sceneNavigator);
        sceneNavigator.setMessageUserScene(messageUserScene);

        Scene createAccountScene = initializeScene("Create User Account.fxml", sceneNavigator);
        sceneNavigator.setCreateAccountScene(createAccountScene);

        Scene eventEnrollScene = initializeScene("Enroll Event.fxml", sceneNavigator);
        sceneNavigator.setEventEnrollScene(eventEnrollScene);

        Scene eventUnEnrollScene = initializeScene("Unenroll Event.fxml", sceneNavigator);
        sceneNavigator.setEventUnEnrollScene(eventUnEnrollScene);

        Scene eventCancelScene = initializeScene("Cancel Event.fxml", sceneNavigator);
        sceneNavigator.setEventCancelScene(eventCancelScene);

        Scene eventCreationScene = initializeScene("Create Event.fxml", sceneNavigator);
        sceneNavigator.setEventCreationScene(eventCreationScene);

        Scene messageAllAttendingEventScene = initializeScene("Message All Event Attendees.fxml", sceneNavigator);
        sceneNavigator.setMessageAllAttendingEventScene(messageAllAttendingEventScene);

        Scene messageAllSpeakersScene = initializeScene("Message All Speakers.fxml", sceneNavigator);
        sceneNavigator.setMessageAllSpeakersScene(messageAllSpeakersScene);

        Scene messageAllAttendeesScene = initializeScene("Message All Attendees.fxml", sceneNavigator);
        sceneNavigator.setMessageAllAttendeesScene(messageAllAttendeesScene);

        Scene seeScheduleScene = initializeScene("See Event Schedule.fxml", sceneNavigator);
        sceneNavigator.setSeeScheduleScene(seeScheduleScene);

        Scene changeEventCapacityScene = initializeScene("Change Event Capacity.fxml", sceneNavigator);
        sceneNavigator.setChangeEventCapacityScene(changeEventCapacityScene);

        Scene deleteAccountScene = initializeScene("Delete User Account.fxml", sceneNavigator);
        sceneNavigator.setDeleteAccountScene(deleteAccountScene);

    }

    private Scene initializeScene(String fxmlPath, SceneNavigator sceneNavigator) {
        Scene scene;
        try {
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

    private Scene initializeLoginScene(String fxmlPath, SceneNavigator sceneNavigator, LoginListener listener) {
        Scene scene;
        try {
            URL url = new File(fxmlPath).toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            scene = new Scene(loader.load());
            LoginController controller = loader.getController();
            controller.setUserManager(useCaseHandler.getUserManager());
            controller.setMessageManager(useCaseHandler.getMessageManager());
            controller.setEventManager(useCaseHandler.getEventManager());
            controller.setSceneNavigator(sceneNavigator);
            controller.addLoginListener(listener);
        } catch (IOException e) {
            scene = new Scene(new VBox(), 800, 600);
        }
        return scene;
    }
}
