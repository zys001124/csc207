package gateways;

import com.google.firebase.database.FirebaseDatabase;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

/**
 * A class that creates and holds the gateway classes necessary for
 * this specific program to function
 */
public class GatewayInitializer {

    // Gateways
    private final UserGateway userGateway;
    private final MessageGateway messageGateway;
    private final EventGateway eventGateway;

    /**
     * Initialize a UserGateway, a MessageGateway, and a EventGateWay
     * from a a Firebase Realtime Database
     *
     * @param um - The UserManager the program will be using
     * @param mm - The MessageManager the program will be using
     * @param em - The EventManager the program will be using
     */
    public GatewayInitializer(UserManager um, MessageManager mm, EventManager em) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        userGateway = new UserGateway(um, database);
        messageGateway = new MessageGateway(mm, database);
        eventGateway = new EventGateway(em, database);
    }

    /**
     * Getter method for the UserGateway
     *
     * @return the UserGateway the program is currently using
     */
    public UserGateway getUserGateway() {
        return userGateway;
    }

    /**
     * Getter method for the MessageGateway
     *
     * @return the MessageGateway the program is currently using
     */
    public MessageGateway getMessageGateway() {
        return messageGateway;
    }

    /**
     * Getter method for the EventGateway
     *
     * @return the EventGateway the program is currently using
     */
    public EventGateway getEventGateway() {
        return eventGateway;
    }

}
