import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import controllers.SceneNavigator;
import gateways.GatewayInitializer;
import gateways.SceneViewInitializer;
import javafx.stage.Stage;
import observers.ObserversInitializer;
import useCaseClasses.UseCaseHolder;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * A conference system for managing the creation/modification/deletion
 * of Users, Events, and Messages at a tech conference
 */
public class ConferenceSystem {

    private final UseCaseHolder useCaseHolder;
    private GatewayInitializer gatewayInitializer;
    private final ObserversInitializer observersInitializer;
    private SceneViewInitializer sceneViewInitializer;

    /**
     * Constructor for a ConferenceSystem object
     * Sets up the program such that it can run properly
     */
    public ConferenceSystem(Stage applicationStage) {
        initializeFirebase();
        useCaseHolder = new UseCaseHolder();
        gatewayInitializer = getGatewayInitializer(useCaseHolder);
        observersInitializer = getObserversInitializer(gatewayInitializer);
        addUseCaseObservers(useCaseHolder, observersInitializer);
        sceneViewInitializer = getSceneInitializer(applicationStage, useCaseHolder);
    }

    /**
     * Begins process of running the program
     */
    public void run() {
        SceneNavigator sceneNavigator = sceneViewInitializer.getSceneNavigator();
        sceneNavigator.showGUI();
//        Scene loginScene = initializeLoginScene("loginScene.fxml", sceneNavigator,
//                this::initializeScenes);
//        sceneNavigator.setLoginScene(loginScene);
//
//        // Default to login scene
//        sceneNavigator.switchSceneView(SceneNavigator.SceneViewType.LOGIN);
//        sceneNavigator.getApplicationStage().show();
    }

    private void initializeFirebase() {
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

    private GatewayInitializer getGatewayInitializer(UseCaseHolder holder) {
        gatewayInitializer = new GatewayInitializer(holder.getUserManager(),
                holder.getMessageManager(), holder.getEventManager());
        return gatewayInitializer;
    }

    private ObserversInitializer getObserversInitializer(GatewayInitializer gatewayInitializer) {
        return new ObserversInitializer(gatewayInitializer.getUserGateway(),
                gatewayInitializer.getMessageGateway(),
                gatewayInitializer.getEventGateway());
    }

    private SceneViewInitializer getSceneInitializer(Stage primaryStage, UseCaseHolder holder) {
        sceneViewInitializer = new SceneViewInitializer(primaryStage, holder.getUserManager(), holder.getMessageManager(), holder.getEventManager());
        return sceneViewInitializer;

    }


    private void addUseCaseObservers(UseCaseHolder useCaseHolder, ObserversInitializer observersInitializer) {
        useCaseHolder.addUserManagerDatabaseObserver(observersInitializer.getUserUpdateDatabaseObserver());
        useCaseHolder.addMessageManagerDatabaseObserver(observersInitializer.getMessageUpdateDatabaseObserver());
        useCaseHolder.addEventManagerDatabaseObserver(observersInitializer.getEventUpdateDatabaseObserver());
    }
//    private void initializeScenes() {
//
//        Scene speakerMenuInputScene = initializeScene("Speaker Main Menu.fxml", sceneNavigator);
//        sceneNavigator.setSpeakerMenuInputScene(speakerMenuInputScene);
//
//        Scene organizerMenuInputScene = initializeScene("Organizer Main Menu.fxml", sceneNavigator);
//        sceneNavigator.setOrganizerMenuInputScene(organizerMenuInputScene);
//
//        Scene attendeeMenuInputScene = initializeScene("Attendee Main Menu.fxml", sceneNavigator);
//        sceneNavigator.setAttendeeMenuInputScene(attendeeMenuInputScene);
//
//        Scene vipMenuInputScene = initializeScene("VIP Main Menu.fxml", sceneNavigator);
//        sceneNavigator.setVipMenuInputScene(vipMenuInputScene);
//
//        Scene adminMenuInputScene = initializeScene("Admin Main Menu.fxml", sceneNavigator);
//        sceneNavigator.setAdminMenuInputScene(adminMenuInputScene);
//
//        Scene messageUserScene = initializeScene("Message User.fxml", sceneNavigator);
//        sceneNavigator.setMessageUserScene(messageUserScene);
//
//        Scene createAccountScene = initializeScene("Create User Account.fxml", sceneNavigator);
//        sceneNavigator.setCreateAccountScene(createAccountScene);
//
//        Scene eventEnrollScene = initializeScene("Enroll Event.fxml", sceneNavigator);
//        sceneNavigator.setEventEnrollScene(eventEnrollScene);
//
//        Scene eventUnEnrollScene = initializeScene("Unenroll Event.fxml", sceneNavigator);
//        sceneNavigator.setEventUnEnrollScene(eventUnEnrollScene);
//
//        Scene eventCancelScene = initializeScene("Cancel Event.fxml", sceneNavigator);
//        sceneNavigator.setEventCancelScene(eventCancelScene);
//
//        Scene eventCreationScene = initializeScene("Create Event.fxml", sceneNavigator);
//        sceneNavigator.setEventCreationScene(eventCreationScene);
//
//        Scene messageAllAttendingEventScene = initializeScene("Message All Event Attendees.fxml", sceneNavigator);
//        sceneNavigator.setMessageAllAttendingEventScene(messageAllAttendingEventScene);
//
//        Scene messageAllSpeakersScene = initializeScene("Message All Speakers.fxml", sceneNavigator);
//        sceneNavigator.setMessageAllSpeakersScene(messageAllSpeakersScene);
//
//        Scene messageAllAttendeesScene = initializeScene("Message All Attendees.fxml", sceneNavigator);
//        sceneNavigator.setMessageAllAttendeesScene(messageAllAttendeesScene);
//
//        Scene seeScheduleScene = initializeScene("See Event Schedule.fxml", sceneNavigator);
//        sceneNavigator.setSeeScheduleScene(seeScheduleScene);
//
//        Scene changeEventCapacityScene = initializeScene("Change Event Capacity.fxml", sceneNavigator);
//        sceneNavigator.setChangeEventCapacityScene(changeEventCapacityScene);
//
//        Scene deleteAccountScene = initializeScene("Delete User Account.fxml", sceneNavigator);
//        sceneNavigator.setDeleteAccountScene(deleteAccountScene);
//
//    }
//
//    private Scene initializeScene(String fxmlPath, SceneNavigator sceneNavigator) {
//        Scene scene;
//        try {
//            URL url = new File(fxmlPath).toURI().toURL();
//            FXMLLoader loader = new FXMLLoader(url);
//            scene = new Scene(loader.load());
//            Controller controller = loader.getController();
//            controller.setUserManager(useCaseHolder.getUserManager());
//            controller.setMessageManager(useCaseHolder.getMessageManager());
//            controller.setEventManager(useCaseHolder.getEventManager());
//            controller.setSceneNavigator(sceneNavigator);
//        } catch (IOException e) {
//            scene = new Scene(new VBox(), 800, 600);
//        }
//        return scene;
//    }
//
//    private Scene initializeLoginScene(String fxmlPath, SceneNavigator sceneNavigator, LoginListener listener) {
//        Scene scene;
//        try {
//            URL url = new File(fxmlPath).toURI().toURL();
//            FXMLLoader loader = new FXMLLoader(url);
//            scene = new Scene(loader.load());
//            LoginController controller = loader.getController();
//            controller.setUserManager(useCaseHolder.getUserManager());
//            controller.setMessageManager(useCaseHolder.getMessageManager());
//            controller.setEventManager(useCaseHolder.getEventManager());
//            controller.setSceneNavigator(sceneNavigator);
//            controller.addLoginListener(listener);
//        } catch (IOException e) {
//            scene = new Scene(new VBox(), 800, 600);
//        }
//        return scene;
//    }
}
