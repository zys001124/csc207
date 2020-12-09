package useCaseClasses;

import entities.Event;
import entities.Message;
import entities.User;
import exceptions.InvalidUserTypeException;
import exceptions.NoMessageException;
import observers.Observable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Represents an messageManager that can store/create messages sent by users of the conference
 */
public class MessageManager extends Observable {

    private final List<Message> messages;

    /**
     * Create a new MessageManager with no messages stored
     */
    public MessageManager() {
        messages = new ArrayList<>();
    }


    /**
     * Adds a message to the list of messages.
     * Throws InvalidUserTypeException if <recipient> has a type which cannot be messaged by <sender>.
     * Throws NoMessageException if the type of <sender> needs to have previous message history
     * with the type of <recipient> in order to send the message.
     *
     * @param sender    the User sending the message
     * @param recipient the User receiving the message
     * @param message   the message that is sent
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
        notifyObservers(messagesToAdd, true);
    }

    /**
     * Sends a messages to users by their given UUID. Can be up to multiple messages.
     *
     * @param sender    the UUID of the sender
     * @param recipient the UUID of the recipient
     * @param message   message that is to be sent
     */
    public void sendMessageById(UUID sender, UUID recipient, String message) {

        List<Message> messagesToAdd = new ArrayList<>();

        messagesToAdd.add(new Message(message, sender, recipient, UUID.randomUUID()));
        messages.addAll(messagesToAdd);
        notifyObservers(messagesToAdd, true);
    }

    /**
     * Messages that are to be added to the conference system
     *
     * @param sender    the UUID of the sender
     * @param receiver  the UUID of the receiver
     * @param message   the message to be sent
     * @param timeSent  time of message sent
     * @param messageId UUID of the message sent
     */
    public void addMessage(UUID sender, UUID receiver, String message, LocalDateTime timeSent, UUID messageId) {
        List<Message> messagesToAdd = new ArrayList<>();
        messagesToAdd.add(new Message(message, sender, receiver, messageId, timeSent));
        messages.addAll(messagesToAdd);
        notifyObservers(messagesToAdd, true);
    }

    /**
     * Messages that are to be added to the conference system
     *
     * @param message the message object to be added
     */
    public void addMessage(Message message) {
        List<Message> messagesToAdd = new ArrayList<>();
        messagesToAdd.add(message);
        messages.addAll(messagesToAdd);
        notifyObservers(messagesToAdd, true);
    }


    /**
     * Messages all of the users attending a certain event
     *
     * @param message Message that is to be sent to all the users in the event
     * @param e       the event that the speaker is messaging all of the usres in
     * @param sender  the UUID of the speaker sending the message
     */
    public void messageAllAttendingEvent(String message, Event e, UUID sender) {
        List<Message> messagesToAdd = new ArrayList<>();
        for (UUID userId : e) {

            messagesToAdd.add(new Message(message, sender, userId, UUID.randomUUID()));
        }
        messages.addAll(messagesToAdd);
        notifyObservers(messagesToAdd, true);
    }


    /**
     * Takes in two users and checks to see if a message was sent between the two
     *
     * @param sender    UUID of the sender user
     * @param recipient UUID of the recipient user
     * @return Boolean of if a message is sent between two senders
     */
    public boolean messageSentBy(UUID sender, UUID recipient) {
        for (Message message : messages) {
            if (message.getSenderId().equals(sender) &&
                    message.getRecipientId().equals(recipient)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Gets an ordered list of the messages sent between two users and returns that list
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
     * Checks if this instance of the manager contains a given message
     *
     * @param message - the message we wish to check
     * @return a boolean - whether or not the message is being kept track of
     * by this instance of MessageManager
     */
    public boolean isMessageInManager(Message message) {
        return messages.contains(message);
    }
}
