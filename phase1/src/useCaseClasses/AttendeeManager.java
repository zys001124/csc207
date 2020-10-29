package useCaseClasses;


import entities.Attendee;
import entities.Message;

import java.util.ArrayList;
import java.util.List;

public class AttendeeManager {

    private List<Attendee> attendees;
    private List<Message> messages;

    public AttendeeManager(){
        attendees = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public AttendeeManager(List<Attendee> attendees, List<Message> messages){
        this.attendees = attendees;
        this.messages = messages;
    }

    public void addAttendee(Attendee attendee){
        attendees.add(attendee);
    }

    public void addMessage(Message m){
        messages.add(m);
    }

    public Attendee removeAttendee(int i){
        return attendees.remove(i);
    }

    public List<Attendee> getAttendees(){
        return attendees;
    }

    public List<Message> getMessages(){
        return messages;
    }

    public void attendeeLogin(Attendee a, String username, String password){
        //TODO
    }


}
