package gateways;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import entities.Event;
import entities.Message;
import entities.User;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class FirebaseGatewayOld {

    public boolean allowWrite = true;
    public boolean allowUsersRead = true;
    public boolean allowEventsRead = true;
    public boolean allowMessagesRead = true;
    private final UserManager userManager;
    private final EventManager eventManager;
    private final MessageManager messageManager;
    private final FirebaseDatabase db;
    private final DatabaseReference usersRef;
    private final DatabaseReference eventsRef;
    private final DatabaseReference messagesRef;

    public FirebaseGatewayOld(UserManager um, EventManager em, MessageManager mm) {
        userManager = um;
        eventManager = em;
        messageManager = mm;

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

        db = FirebaseDatabase.getInstance();
        usersRef = db.getReference().child("Users");
        eventsRef = db.getReference().child("Events");
        messagesRef = db.getReference().child("Messages");

        addSnapShotListenersAndLoadFromFirebase();
    }

    private void addSnapShotListenersAndLoadFromFirebase() {
        if (allowUsersRead) {
            getUsers();
        }
        if (allowMessagesRead) {
            getMessages();
        }
        if (allowEventsRead) {
            getEvents();
        }

    }

    private void getUsers() {
        usersRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User.UserData userData = dataSnapshot.getValue(User.UserData.class);
                userManager.addUserFromDatabase(userData);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                User.UserData userData = dataSnapshot.getValue(User.UserData.class);
                userManager.changeUserFromDatabase(userData);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                User.UserData userData = dataSnapshot.getValue(User.UserData.class);
                userManager.removeUser(UUID.fromString(userData.uuid), true);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void getMessages() {
        messagesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Message.MessageData mData = dataSnapshot.getValue(Message.MessageData.class);
                messageManager.addMessageFromDatabase(mData);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getEvents() {
        eventsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Event.EventData data = eventDataFromDataSnapshot(dataSnapshot);
                eventManager.addEventFromDatabase(data);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Event.EventData data = eventDataFromDataSnapshot(dataSnapshot);
                eventManager.updateEventFromDatabase(data);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Event.EventData data = eventDataFromDataSnapshot(dataSnapshot);
                eventManager.removeEventFromDataBase(UUID.fromString(data.eventId));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void pushUsers(List<User> users) {
        for (User user : users) {
            usersRef.child(user.getUsername()).setValueAsync(user.getUserData());
        }
    }

    public void removeUsers(List<User> users) {
        for (User user : users) {
            usersRef.child(user.getUsername()).removeValueAsync();
        }
    }

    public void pushMessages(List<Message> messages) {
        for (Message message : messages) {
            messagesRef.child(message.getId().toString()).setValueAsync(message.getMessageData());
        }
    }

    public void removeMessages(List<Message> messages) {
        for (Message message : messages) {
            messagesRef.child(message.getId().toString()).removeValueAsync();
        }
    }

    public void pushEvents(List<Event> events) {
        for (Event event : events) {
            Event.EventData eventData = event.getEventData();
            eventsRef.child(event.getId().toString()).setValueAsync(eventData);
            eventsRef.child(event.getId().toString()).child("attendees").setValueAsync(eventData.attendees);
            eventsRef.child(event.getId().toString()).child("speakerIds").setValueAsync(eventData.speakerIds);
        }
    }

    public void removeEvents(List<Event> events) {
        for (Event event : events) {
            eventsRef.child(event.getId().toString()).removeValueAsync();
        }
    }

    private Event.EventData eventDataFromDataSnapshot(DataSnapshot dataSnapshot) {

        Map eventMap = (Map<String, Object>) dataSnapshot.getValue();
        Event.EventData eventData = new Event.EventData();
        if (eventMap.containsKey("attendees")) {
            eventData.attendees = (Collection<String>) eventMap.get("attendees");
        } else {
            eventData.attendees = new ArrayList<>();
        }

        if (eventMap.containsKey("speakerIds")) {
            eventData.speakerIds = (Collection<String>) eventMap.get("speakerIds");
        } else {
            eventData.speakerIds = new ArrayList<>();
        }
        eventData.eventSTime = (String) eventMap.get("eventSTime");
        eventData.eventETime = (String) eventMap.get("eventETime");
        eventData.organizerId = (String) eventMap.get("organizerId");
        eventData.VIPonly = (String) eventMap.get("VIPonly");
        eventData.eventCapacity = (String) eventMap.get("eventCapacity");
        eventData.eventRoom = (String) eventMap.get("eventRoom");
        eventData.eventId = (String) eventMap.get("eventId");
        eventData.eventTitle = (String) eventMap.get("eventTitle");

        return eventData;

    }

}
