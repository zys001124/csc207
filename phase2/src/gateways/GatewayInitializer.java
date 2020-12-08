package gateways;

import com.google.firebase.database.FirebaseDatabase;
import entities.Event;
import entities.Message;
import entities.User;
import observers.UpdateDatabaseObserver;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

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
