package gateways;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.cloud.FirestoreClient;
import entities.User;
import exceptions.UserTypeDoesNotExistException;
import exceptions.UsernameAlreadyExistsException;
import useCaseClasses.UserManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;

public class FirebaseGateway {

    private UserManager userManager;
    private Firestore db;

    public FirebaseGateway(UserManager um) {
        userManager = um;
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

        db = FirestoreClient.getFirestore();
    }

    public void loadEntities() {
        getUsers();
    }

    private void addFutureCallbacks(ApiFuture<QuerySnapshot> future, String collectionName) {
        ApiFutures.addCallback(future, new ApiFutureCallback<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot result) {
               // System.out.println("Operation completed with result: " + s);
                if(collectionName.equals("Users")) {
                    onUsersGotten(result.getDocuments());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //System.out.println("Operation failed with error: " + t);
            }
        });
    }

    private void getUsers() {
        ApiFuture<QuerySnapshot> userFuture = db.collection("Users").get();
        addFutureCallbacks(userFuture, "Users");
    }

    private void onUsersGotten(List<QueryDocumentSnapshot> documentSnapshotList) {
        for(int i = 0; i<documentSnapshotList.size(); i++) {
            QueryDocumentSnapshot qds = documentSnapshotList.get(0);

            User.UserType type = null;
            try {
                type = userManager.parseType(qds.get("type").toString());
            } catch (UserTypeDoesNotExistException e) {
                e.printStackTrace();
                System.exit(-1);
            }
            String username = qds.get("username").toString();
            String password = qds.get("password").toString();
            UUID uuid = UUID.fromString(qds.get("uuid").toString());

            try {
                userManager.createUser(type, username, password, uuid);
            } catch (UsernameAlreadyExistsException e)  {
                //TODO handle this
                // Ignore for now
            }
        }
    }

}
