package gateways;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Executor;

public class FirebaseGateway {

    public  void start() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("conference-system-group-0186-key.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://conference-system-group-0186.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException exception) {
            exception.printStackTrace();
            System.exit(-1);
        }

       // FirebaseAuth auth = FirebaseAuth.getInstance();

        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = db.collection("testData").get();

        addFutureCallbacks(future);
    }

    private void addFutureCallbacks(ApiFuture<QuerySnapshot> future) {
        ApiFutures.addCallback(future, new ApiFutureCallback<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot result) {
                String s = result.getDocuments().get(0).get("str1").toString();
               // System.out.println("Operation completed with result: " + s);
            }

            @Override
            public void onFailure(Throwable t) {
                //System.out.println("Operation failed with error: " + t);
            }
        });
    }
}
