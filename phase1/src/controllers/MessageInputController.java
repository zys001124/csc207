package controllers;

import entities.Attendee;
import entities.Message;
import useCaseClasses.AttendeeManager;
import useCaseClasses.MessageManager;

import java.util.UUID;

public class MessageInputController {

    private AttendeeManager attendeeManager;
    private MessageManager messageManager;

    public MessageInputController(AttendeeManager am, MessageManager mm) {
        attendeeManager = am;
        messageManager = mm;
    }

    public void sendMessage(UUID receiver, String message)
    {
        messageManager.addMessage(attendeeManager.getCurrentlyLoggedIn(), receiver, message);
    }
}
