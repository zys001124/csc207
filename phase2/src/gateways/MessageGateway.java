package gateways;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import entities.Message;
import gateways.snapshotreaders.DataSnapshotReader;
import gateways.snapshotreaders.MessageDataSnapshotReader;
import useCaseClasses.MessageManager;

import java.util.List;

/**
 * A gateway for handling the saving/loading of Message data from
 * a Firebase Realtime Database
 */
public class MessageGateway extends FirebaseGateway<Message> {

    private final MessageManager messageManager;

    /**
     * The constructor for an MessageGateway object
     *
     * @param mm       - The MessageManager the program is using
     * @param database - A FirebaseDatabase object that will be used to create
     *                 a reference to the directory in the database holding
     *                 Message information
     */
    public MessageGateway(MessageManager mm, FirebaseDatabase database) {
        super("Messages", database);
        messageManager = mm;
    }

    /**
     * This method pushes the information of a list of Messages to
     * the Firebase Realtime Database
     *
     * @param messages - the list of Messages to save information of
     */
    @Override
    public void pushEntities(List<Message> messages) {
        for (Message message : messages) {
            databaseReference.child(message.getId().toString()).setValueAsync(message.getMessageData());
        }
    }

    /**
     * This method removes the information of a list of Messages to
     * the Firebase Realtime Database
     *
     * @param messages - the list of Messages to remove information of
     */
    @Override
    public void removeEntities(List<Message> messages) {
        for (Message message : messages) {
            databaseReference.child(message.getId().toString()).removeValueAsync();
        }
    }

    /**
     * This method is called when a piece of information is added under
     * the directory in the database that holds Message information
     *
     * @param dataSnapshot - an object containing the information that has been added
     * @param s            - the name of the node before being added (it will be null since the node didnt
     *                     exist before)
     */
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Message m = snapshotReader.getFromDataSnapshot(dataSnapshot);
        if (!messageManager.isMessageInManager(m)) messageManager.addMessage(m);
    }

    /**
     * This method is called when a piece of information is changed under
     * the directory in the database that holds Message information
     *
     * @param dataSnapshot - an object containing the information that has been changed
     * @param s            - the name of the node before being added
     */
    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    /**
     * This method is called when a piece of information is removed under
     * the directory in the database that holds Message information
     *
     * @param dataSnapshot - an object containing the information that has been removed
     */
    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    /**
     * Gets the snapshotReader
     *
     * @return a MessageDataSnapshotReader
     */
    @Override
    protected DataSnapshotReader<Message> getSnapshotReader() {
        if (snapshotReader == null) {
            snapshotReader = new MessageDataSnapshotReader();
        }
        return snapshotReader;
    }
}
