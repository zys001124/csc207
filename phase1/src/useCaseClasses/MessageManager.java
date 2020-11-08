package useCaseClasses;

import entities.Attendee;
import entities.Message;
import exceptions.NoMessageException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MessageManager {

    private List<Message> messages;

    public MessageManager(){
        messages = new ArrayList<>();
    }

    public MessageManager(List<Message> messages){
        this.messages = messages;
    }

    public void addMessage(UUID sender, UUID receiver, String message){

        messages.add(new Message(message, sender, receiver, UUID.randomUUID()));
    }

    public List<Message> getMessages(){
        return messages;
    }

    public Message findMessage(Message m) throws NoMessageException{

        for(Message message : messages) {
            if (message == m) {
                return message;
            }
        }
        throw new NoMessageException("No message exists in the system.");
        //TODO: Is this exception needed?
    }

    public List<Message> messagesBetweenTwo(Attendee a1, Attendee a2){
        //TODO: Returns an orderd list of the messages sent between a1 and a2.
        UUID a1id = a1.getId();
        UUID a2id = a2.getId();
        List<Message> theMessages = new ArrayList<>();

        for(Message m : messages){
            if((m.getSenderId() == a1id && m.getRecipientId() == a2id) || (m.getSenderId() == a2id && m.getRecipientId()
                    == a1id)) {
                theMessages.add(m);
            }
        }

        int len = theMessages.size();
        for(int i = 0; i < len-1; i++){
            for(int j = 1; j<len-i-1; j++){
                //TODO im not sure if this actually works because the int could only be for years. Need to check up on this.
                if(theMessages.get(j).getTimeSent().compareTo(theMessages.get(j+1).getTimeSent()) > 0){
                    Message tempj = theMessages.get(j);
                    Message tempjp1 = theMessages.get(j+1);
                    theMessages.set(j, tempjp1);
                    theMessages.set(j+1, tempj);
                }
            }
        }
        return theMessages;
    }
}
