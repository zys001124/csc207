package useCaseClasses;

import entities.Attendee;
import entities.Message;

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

        messages.add(new Message(message, sender, receiver));
    }

    public List<Message> getMessages(){
        return messages;
    }

    public Message findMessage(Message m){
        //TODO: Find the message in the list
        return m; //TEMPORARY
    }

    public List<Message> messagesBetweenTwo(Attendee a1, Attendee a2){
        //TODO: Returns an orderd list of the messages sent between a1 and a2.
        return null;
    }
}
