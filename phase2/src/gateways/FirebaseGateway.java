package gateways;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import entities.User;
import exceptions.UserTypeDoesNotExistException;
import exceptions.UsernameAlreadyExistsException;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class FirebaseGateway {

    private UserManager userManager;
    private EventManager eventManager;
    private MessageManager messageManager;
    private Firestore db;

    public FirebaseGateway(UserManager um, EventManager em, MessageManager mm) {
        userManager = um;
        eventManager = em;
        messageManager = mm;

        try {
            FileInputStream serviceAccount =
                    new FileInputStream("C:/Users/z1094/IdeaProjects/group_0186/phase2/resources/conference-system-group-0186-key.json");

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
        getEvents();
        getMessages();
    }

    private void addFutureCallbacks(ApiFuture<QuerySnapshot> future, String collectionName) {
        ApiFutures.addCallback(future, new ApiFutureCallback<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot result) {
               // System.out.println("Operation completed with result: " + s);
                if(collectionName.equals("Users")) {
                    onUsersGotten(result.getDocuments());
                }
                else if (collectionName.equals("Events")) {
                    onEventsGotten(result.getDocuments());
                }
                else if(collectionName.equals("Messages")) {
                    onMessagesGotten(result.getDocuments());
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

    private void getEvents() {
        ApiFuture<QuerySnapshot> eventFuture = db.collection("Events").get();
        addFutureCallbacks(eventFuture, "Events");
    }

    private void onEventsGotten(List<QueryDocumentSnapshot> documentSnapshotList) {
        for(int i = 0; i<documentSnapshotList.size(); i++) {
            QueryDocumentSnapshot qds = documentSnapshotList.get(0);


            String title = qds.get("title").toString();
            LocalDateTime startTime = convertToLocalDateViaInstant(((Timestamp)qds.get("startTime")).toDate());
            LocalDateTime endTime = convertToLocalDateViaInstant(((Timestamp)qds.get("endTime")).toDate());
            UUID eventId = UUID.fromString(qds.get("uuid").toString());
            UUID organizerId = UUID.fromString(qds.get("organizerId").toString());
            int room = Integer.parseInt(qds.get("room").toString());
            int capacity = Integer.parseInt(qds.get("capacity").toString());
            boolean VIPonly = Boolean.parseBoolean(qds.get("viponly").toString());

            List<UUID> speakers = new ArrayList<>();
            for(String uuid : (List<String>) Objects.requireNonNull(qds.get("speakerIds"))) {
                speakers.add(UUID.fromString(uuid));
            }
            List<UUID> attendees = new ArrayList<>();
            for(String uuid : (List<String>) Objects.requireNonNull(qds.get("attendeeIds"))) {
                attendees.add(UUID.fromString(uuid));
            }
            eventManager.addEvent(title, startTime, endTime, eventId, organizerId, speakers, attendees, room, capacity, VIPonly);
        }
    }

    private void getMessages() {
        ApiFuture<QuerySnapshot> messageFuture = db.collection("Messages").get();
        addFutureCallbacks(messageFuture, "Messages");
    }

    private void onMessagesGotten(List<QueryDocumentSnapshot> documentSnapshotList) {
        for(int i = 0; i<documentSnapshotList.size(); i++) {
            QueryDocumentSnapshot qds = documentSnapshotList.get(0);

            String messageText = qds.get("messageText").toString();
            UUID senderId = UUID.fromString(qds.get("senderId").toString());
            UUID recipientId = UUID.fromString(qds.get("recipientId").toString());
            UUID messageId = UUID.fromString(qds.get("messageId").toString());
            LocalDateTime timeSent = convertToLocalDateViaInstant(((Timestamp)qds.get("timeSent")).toDate());

            messageManager.addMessage(senderId, recipientId, messageText, timeSent, messageId);
        }
    }

    public LocalDateTime convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
    
    
}
