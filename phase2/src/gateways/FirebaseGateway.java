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
import com.google.firestore.v1beta1.Document;
import entities.Event;
import entities.Message;
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
            QueryDocumentSnapshot qds = documentSnapshotList.get(i);

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
                System.out.println(username);
            }
        }
    }

    private void getEvents() {
        ApiFuture<QuerySnapshot> eventFuture = db.collection("Events").get();
        addFutureCallbacks(eventFuture, "Events");
    }

    private void onEventsGotten(List<QueryDocumentSnapshot> documentSnapshotList) {
        for(int i = 0; i<documentSnapshotList.size(); i++) {
            QueryDocumentSnapshot qds = documentSnapshotList.get(i);


            String title = qds.get("title").toString();
            LocalDateTime startTime = convertToLocalDateViaInstant(((Timestamp)qds.get("startTime")));
            LocalDateTime endTime = convertToLocalDateViaInstant(((Timestamp)qds.get("endTime")));
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
            QueryDocumentSnapshot qds = documentSnapshotList.get(i);

            String messageText = qds.get("messageText").toString();
            UUID senderId = UUID.fromString(qds.get("senderId").toString());
            UUID recipientId = UUID.fromString(qds.get("recipientId").toString());
            UUID messageId = UUID.fromString(qds.get("messageId").toString());
            LocalDateTime timeSent = convertToLocalDateViaInstant((Timestamp)qds.get("timeSent"));

            messageManager.addMessage(senderId, recipientId, messageText, timeSent, messageId);
        }
    }

    public LocalDateTime convertToLocalDateViaInstant(Timestamp dateToConvert) {
        return dateToConvert.toDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public Timestamp convertToTimestamp(LocalDateTime dateToConvert) {

        return Timestamp.ofTimeSecondsAndNanos(dateToConvert.getSecond(), dateToConvert.getNano());
    }
    
    public void pushUsers() {
        CollectionReference usersRef = db.collection("Users");
        for(User user : userManager.getUsers()) {
            HashMap<String, Object> userData = new HashMap<>();

            userData.put("username", user.getUsername());
            userData.put("password", user.getPassword());
            userData.put("uuid", user.getId().toString());
            userData.put("type", user.getType().toString());

            usersRef.document(user.getUsername()).set(userData);
        }
    }

    public void pushEvents() {
        CollectionReference eventsRef = db.collection("Events");
        for(Event event : eventManager.getEvents()) {
            HashMap<String, Object> eventData = new HashMap<>();

            eventData.put("title", event.getEventTitle());
            eventData.put("capacity", event.getEventCapacity());
            eventData.put("room", event.getEventRoom());
            eventData.put("uuid", event.getId().toString());
            eventData.put("viponly", event.getViponly());
            eventData.put("organizerId", event.getOrganizerId().toString());
            eventData.put("startTime", convertToTimestamp(event.getEventTime()));
            eventData.put("endTime", convertToTimestamp(event.getEventETime()));

            List<String> speakers = new ArrayList<>();
            for(UUID uuid : event.getSpeakerId()) {
                speakers.add(uuid.toString());
            }
            List<String> attendees = new ArrayList<>();
            for(UUID uuid : event) {
                attendees.add(uuid.toString());
            }

            eventData.put("speakerIds", speakers);
            eventData.put("attendeeIds", attendees);

            eventsRef.document(event.getEventTitle()).set(eventData);
        }
    }

    public void pushMessages() {
        CollectionReference messagesRef = db.collection("Messages");
        for(Message message : messageManager.getMessages()) {
            HashMap<String, Object> messageData = new HashMap<>();

            messageData.put("messageText", message.getMessageText());
            messageData.put("messageId", message.getId().toString());
            messageData.put("senderId", message.getSenderId().toString());
            messageData.put("recipientId", message.getRecipientId().toString());
            messageData.put("timeSent", convertToTimestamp(message.getTimeSent()));

            DocumentReference messageDoc = messagesRef.document(message.getId().toString());

            messageDoc.set(messageData);
        }
    }


}
