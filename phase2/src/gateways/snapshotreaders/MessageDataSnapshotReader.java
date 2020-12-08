package gateways.snapshotreaders;

import com.google.firebase.database.DataSnapshot;
import entities.Message;

/**
 * A DataSnapShotReader that can parse Message objects from DataSnapshots
 */
public class MessageDataSnapshotReader implements DataSnapshotReader<Message> {
    /**
     * Gets the message entity from the data snapshot given linked to firebase updates
     *
     * @param dataSnapshot the data snapshot to be passed in for the Message
     * @return Message entity for this system
     */
    @Override
    public Message getFromDataSnapshot(DataSnapshot dataSnapshot) {
        Message.MessageData mData = dataSnapshot.getValue(Message.MessageData.class);
        return Message.fromMessageData(mData);
    }
}
