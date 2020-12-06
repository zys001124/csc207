package gateways;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import entities.Message;
import useCaseClasses.MessageManager;

import java.util.List;

public class MessageGateway extends FirebaseGateway<Message> {

    private final MessageManager messageManager;

    public MessageGateway(MessageManager mm) {
        super("Messages");
        messageManager = mm;
    }

    @Override
    public void pushEntities(List<Message> messages) {
        for (Message message : messages) {
            databaseReference.child(message.getId().toString()).setValueAsync(message.getMessageData());
        }
    }

    @Override
    public void removeEntities(List<Message> messages) {
        for (Message message : messages) {
            databaseReference.child(message.getId().toString()).removeValueAsync();
        }
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        messageManager.addMessageFromDataSnapshot(dataSnapshot);
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
}
