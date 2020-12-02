package observers;

import entities.Event;
import exceptions.IncorrectObjectTypeException;
import gateways.FirebaseGateway;

import java.util.List;

public class EventManagerObserver implements Observer {

    private FirebaseGateway fbg;

    public EventManagerObserver(FirebaseGateway gateway) {
        fbg = gateway;
    }

    @Override
    public void update(Observable o, List<?> changes, boolean addedOrChanged) throws IncorrectObjectTypeException {

        try {
            if(addedOrChanged){
                fbg.pushEvents((List<Event>) changes);
            }
            else {
                fbg.removeEvents((List<Event>) changes);
            }

        } catch(ClassCastException e) {
            throw new IncorrectObjectTypeException("List of type Message required");
        }
    }
}
