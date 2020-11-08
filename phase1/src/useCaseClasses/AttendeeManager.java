package useCaseClasses;


import entities.Attendee;
import entities.Message;
import exceptions.IncorrectPasswordException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//TODO remove messages reference, polish up. Messages should be handled by MessageManager only
public class AttendeeManager {

    private List<Attendee> attendees;
    private List<Message> messages;

    private UUID currentlyLoggedIn;

    public AttendeeManager(){
        attendees = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public AttendeeManager(List<Attendee> attendees){
        this.attendees = attendees;
    }

    public Attendee getAttendee(UUID id) {
        Attendee search = null;
        for(Attendee a: attendees) {
            if(a.getId() == id) {
                search = a;
                break;
            }
        }
        return search;
    }

    public UUID getCurrentlyLoggedIn() {return currentlyLoggedIn;}

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

    public void attendeeLogin(Attendee a, String username, String password) throws IncorrectPasswordException {
        if(!a.verifyLogin(username, password)){
            throw new IncorrectPasswordException("Incorrect Username or Password.");
        }
        else{
            System.out.println("Username and Password correct. \n Welcome " + username);
        }
    }


}
