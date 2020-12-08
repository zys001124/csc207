package useCaseClasses;

import com.google.firebase.database.DataSnapshot;
import entities.Event;
import entities.Message;
import entities.User;
import exceptions.InvalidUserTypeException;
import exceptions.NoMessageException;
import gateways.DataSnapshotReader;
import observers.Observable;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Represents an messageManager that can store/create messages sent by users of the conference
 */
public class MessageManager extends Observable implements DataSnapshotReader<Message> {

    private final List<Message> messages;

    /**
     * Create a new MessageManager with no messages stored
     */
    public MessageManager() {
        messages = new ArrayList<>();
    }

    /**
     * Create a new MessageManager with all messages contained in <messages>
     */
    public MessageManager(List<Message> messages) {
        this.messages = messages;
    }

    /**
     * Adds a message to the list of messages
     *
     * @param sender   the User sending the message
     * @param recipient the User receiving the message
     * @param message  the message that is sent
     */
    public void sendMessage(User sender, User recipient, String message) throws InvalidUserTypeException, NoMessageException {

        User.UserType senderType = sender.getType();
        User.UserType recipientType = recipient.getType();

        switch (senderType) {
            case ATTENDEE: {
                if (recipientType.equals(User.UserType.ORGANIZER) || recipientType.equals(User.UserType.VIP) || recipientType.equals(User.UserType.ADMIN)) {
                    throw new InvalidUserTypeException(recipientType);
                }
                break;
            }
            case ORGANIZER: {
                if (recipientType.equals(User.UserType.ADMIN)) {
                    throw new InvalidUserTypeException(recipientType);
                }
                break;
            }
            case SPEAKER: {
                if (recipientType.equals(User.UserType.ADMIN)) {
                    throw new InvalidUserTypeException(recipientType);
                } else if (recipientType.equals(User.UserType.ATTENDEE) && !messageSentBy(recipient.getId(), recipient.getId())) {
                    throw new NoMessageException();
                }
                break;
            }
            case VIP: {
                if (recipientType.equals(User.UserType.ADMIN)) {
                    throw new InvalidUserTypeException(recipientType);
                }
            }
        }

        List<Message> messagesToAdd = new ArrayList<>();

        messagesToAdd.add(new Message(message, sender.getId(), recipient.getId(), UUID.randomUUID()));
        messages.addAll(messagesToAdd);
        notifyObservers(messagesToAdd, true, false);
    }

    /**
     * Sends a messages to users by their given UUID. Can be up to multiple messages
     * @param sender the UUID of the sender
     * @param recipient the UUID of the recipient
     * @param message message that is to be sent
     */
    public void sendMessageById(UUID sender, UUID recipient, String message){

        List<Message> messagesToAdd = new ArrayList<>();

        messagesToAdd.add(new Message(message, sender, recipient, UUID.randomUUID()));
        messages.addAll(messagesToAdd);
        notifyObservers(messagesToAdd, true, false);
    }

    /**
     * Messages that are to be added to the conference system
     * @param sender the UUID of the sender
     * @param receiver the UUID of the receiver
     * @param message the message to be sent
     * @param timeSent time of message sent
     * @param messageId UUID of the message sent
     */
    public void addMessage(UUID sender, UUID receiver, String message, LocalDateTime timeSent, UUID messageId) {
        List<Message> messagesToAdd = new ArrayList<>();
        messagesToAdd.add(new Message(message, sender, receiver, messageId, timeSent));
        messages.addAll(messagesToAdd);
        notifyObservers(messagesToAdd, true, false);
    }

    /**
     * Adds a message to the conference system from the firebase update data snapshot
     * @param dataSnapshot the snapshot that contains the message to be added
     */
    public void addMessageFromDataSnapshot(DataSnapshot dataSnapshot) {
        Message message = getFromDataSnapshot(dataSnapshot);

        if (!messageExists(message.getId())) {
            List<Message> messagesToAdd = new ArrayList<>();
            messagesToAdd.add(message);
            messages.addAll(messagesToAdd);
            notifyObservers(messagesToAdd, true, true);
        }
    }

    /**
     * gets a list of all the messages given in this conference system.
     *
     * @return a list of messages
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Messages all of the users attending a certain event
     * @param message Message that is to be sent to all the users in the event
     * @param e the event that the speaker is messaging all of the usres in
     * @param sender the UUID of the speaker sending the message
     */
    public void messageAllAttendingEvent(String message, Event e, UUID sender) {
        List<Message> messagesToAdd = new ArrayList<>();
        for (UUID userId : e) {

            messagesToAdd.add(new Message(message, sender, userId, UUID.randomUUID()));
        }
        messages.addAll(messagesToAdd);
        notifyObservers(messagesToAdd, true, false);
    }


    /**
     * takes in two users and checks to see if a message was sent between the two
     *
     * @param sender   UUID of the sender user
     * @param receiver UUID of teh reciever user
     * @return Boolean of if a message is sent between two senders
     */
    public boolean messageSentBy(UUID sender, UUID receiver) {
        for (Message message : messages) {
            if (message.getSenderId().equals(sender) &&
                    message.getRecipientId().equals(receiver)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if a message exists in the conference system based off of the UUID
     * @param messageID UUID of the message to be checked in the conference system
     * @return boolean on if the message is found
     */
    private boolean messageExists(UUID messageID) {
        for (Message m : messages) {
            if (m.getId().equals(messageID)) {
                return true;
            }
        }
        return false;
    }

    /**
     * gets an ordered list of the messages sent between two users and returns that list
     *
     * @param a1 the first user in the messaging exchange
     * @param a2 the second user in the messaging exchange
     * @return an ordered list of the messages sent between a1 and a2
     */
    public List<Message> messagesBetweenTwo(User a1, User a2) {
        List<Message> theMessages = new ArrayList<>();

        for (Message m : messages) {
            if ((m.getSenderId().equals(a1.getId()) && m.getRecipientId().equals(a2.getId())) ||
                    (m.getSenderId().equals(a2.getId()) && m.getRecipientId().equals(a1.getId()))) {
                theMessages.add(m);
            }
        }
        Collections.sort(theMessages);
        return theMessages;
    }

    /**
     * Gets the message entity from the data snapshot given linked to firebase updates
     * @param dataSnapshot the data snapshot to be passed in for the Message
     * @return Message entity for this system
     */
    @Override
    public Message getFromDataSnapshot(DataSnapshot dataSnapshot) {
        Message.MessageData mData = dataSnapshot.getValue(Message.MessageData.class);
        return Message.fromMessageData(mData);
    }
}
