import com.google.cloud.Timestamp;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import gateways.FirebaseGateway;
import org.threeten.bp.LocalDate;

import java.time.LocalDateTime;


public class Main {

    public static void main(String[] args) {
        ConferenceSystem system = new ConferenceSystem();

        system.run();
    }
}
