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
    private final ObserversInitializer observersInitializer;
    private GatewayInitializer gatewayInitializer;
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
}
