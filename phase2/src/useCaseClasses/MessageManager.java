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
     * @param sender   the UUID of the sender of the message
     * @param receiver the UUID of the recieves of the message
     * @param message  the message that is sent
     */
    public void sendIndividualMessage(User.UserType senderType, UUID sender, User.UserType receiverType, UUID receiver, String message) throws InvalidUserTypeException, NoMessageException {
        switch (senderType) {
            case ATTENDEE: {
                if (receiverType.equals(User.UserType.ORGANIZER) || receiverType.equals(User.UserType.VIP) || receiverType.equals(User.UserType.ADMIN)) {
                    throw new InvalidUserTypeException(receiverType);
                }
                break;
            }
            case ORGANIZER: {
                if (receiverType.equals(User.UserType.ADMIN)) {
                    throw new InvalidUserTypeException(receiverType);
                }
                break;
            }
            case SPEAKER: {
                if (receiverType.equals(User.UserType.ADMIN)) {
                    throw new InvalidUserTypeException(receiverType);
                } else if (receiverType.equals(User.UserType.ATTENDEE) && !messageSentBy(receiver, sender)) {
                    throw new NoMessageException();
                }
                break;
            }
            case VIP: {
                if (receiverType.equals(User.UserType.ADMIN)) {
                    throw new InvalidUserTypeException(receiverType);
                }
            }
        }

        List<Message> messagesToAdd = new ArrayList<>();

        messagesToAdd.add(new Message(message, sender, receiver, UUID.randomUUID()));
        messages.addAll(messagesToAdd);
        notifyObservers(messagesToAdd, true, false);
    }

    public void addMessage(UUID sender, UUID receiver, String message, LocalDateTime timeSent, UUID messageId) {
        List<Message> messagesToAdd = new ArrayList<>();
        messagesToAdd.add(new Message(message, sender, receiver, messageId, timeSent));
        messages.addAll(messagesToAdd);
        notifyObservers(messagesToAdd, true, false);
    }

    public void sendMessageToGroup(UUID sender, Collection<UUID> receivers, String message) {
        List<Message> messagesToAdd = new ArrayList<>();
        for (UUID receiverId : receivers) {
            messagesToAdd.add(new Message(message, sender, receiverId, UUID.randomUUID()));
        }
        messages.addAll(messagesToAdd);
        notifyObservers(messagesToAdd, true, false);
    }

    //public void sendMessageToAttendees()

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

    @Override
    public Message getFromDataSnapshot(DataSnapshot dataSnapshot) {
        Message.MessageData mData = dataSnapshot.getValue(Message.MessageData.class);
        return Message.fromMessageData(mData);
    }
}
