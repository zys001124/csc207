import entities.Attendee;
import entities.Event;
import entities.Message;
import gateways.loaders.AttendeeLoader;
import gateways.loaders.EventLoader;
import gateways.loaders.MessageLoader;
import useCaseClasses.AttendeeManager;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;

import java.io.IOException;
import java.util.List;

public class ConferenceSystem {

    // global final variables
    public static final String ATTENDEE_CSV_PATH = "attendeeData.csv";
    public static final String MESSAGE_CSV_PATH = "messageData.csv";
    public static final String EVENT_CSV_PATH = "eventData.csv";

    private AttendeeManager attendeeManager;
    private MessageManager messageManager;
    private EventManager eventManager;

    // This will insantiate all relevant objects and then present the menu view
    public void run() {
        initializeUseCases();
        initializePresenters();
        initializeControllers();

        // Program loop goes after this I think, not ready for that yet. - Lewis
    }

    private void initializeUseCases() {
        AttendeeLoader attendeeLoader = new AttendeeLoader();
        MessageLoader messageLoader = new MessageLoader();
        EventLoader eventLoader = new EventLoader();

        List<Attendee> attendees;
        List<Message> messages ;
        List<Event> events;

        try {

            attendees = attendeeLoader.loadAll(ATTENDEE_CSV_PATH);
            messages = messageLoader.loadAll(MESSAGE_CSV_PATH);
            events = eventLoader.loadAll(EVENT_CSV_PATH);

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
            return;
        }

        attendeeManager = new AttendeeManager(attendees);
        messageManager = new MessageManager(messages);
        eventManager = new EventManager(events);
    }

    private void initializeControllers() {

    }

    private void initializePresenters() {

    }
}
