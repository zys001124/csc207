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

    public GatewayInitializer(UserManager um, MessageManager mm, EventManager em) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        userGateway = new UserGateway(um, database);
        messageGateway = new MessageGateway(mm, database);
        eventGateway = new EventGateway(em, database);
    }

    public UserGateway getUserGateway() {
        return userGateway;
    }

    public MessageGateway getMessageGateway() {
        return messageGateway;
    }

    public EventGateway getEventGateway() {
        return eventGateway;
    }

}
