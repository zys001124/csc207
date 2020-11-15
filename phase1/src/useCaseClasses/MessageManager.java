package useCaseClasses;

import entities.Event;
import entities.Message;
import entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MessageManager {

    private final List<Message> messages;

    public MessageManager() {
        messages = new ArrayList<>();
    }

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
    public void addMessage(UUID sender, UUID receiver, String message) {

        messages.add(new Message(message, sender, receiver, UUID.randomUUID()));
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

        for (UUID userId : e) {
            messages.add(new Message(message, sender, userId, UUID.randomUUID()));
        }
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
     * gets an ordered list of the messages sent between two users and returns that list
     *
     * @param a1 the first user in the messaging exchange
     * @param a2 the second user in the messaging exchange
     * @return an ordered list of the messages sent between a1 and a2
     */
    public List<Message> messagesBetweenTwo(User a1, User a2) {
        //TODO: Returns an orderd list of the messages sent between a1 and a2.
        UUID a1id = a1.getId();
        UUID a2id = a2.getId();
        List<Message> theMessages = new ArrayList<>();

        for (Message m : messages) {
            if ((m.getSenderId() == a1id && m.getRecipientId() == a2id) || (m.getSenderId() == a2id && m.getRecipientId()
                    == a1id)) {
                theMessages.add(m);
            }
        }

        int len = theMessages.size();
        for (int i = 0; i < len - 1; i++) {
            for (int j = 1; j < len - i - 1; j++) {
                //TODO im not sure if this actually works because the int could only be for years. Need to check up on this.
                if (theMessages.get(j).getTimeSent().isAfter(theMessages.get(j + 1).getTimeSent())) {
                    Message tempj = theMessages.get(j);
                    Message tempjp1 = theMessages.get(j + 1);
                    theMessages.set(j, tempjp1);
                    theMessages.set(j + 1, tempj);
                }
            }
        }
        return theMessages;
    }
}
