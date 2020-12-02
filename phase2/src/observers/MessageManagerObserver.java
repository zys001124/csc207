package observers;

import entities.Message;
import exceptions.IncorrectObjectTypeException;
import gateways.FirebaseGateway;

import java.util.List;

public class MessageManagerObserver implements Observer {

    private FirebaseGateway fbg;

    public MessageManagerObserver(FirebaseGateway gateway) {
        fbg = gateway;
    }

    @Override
    public void update(Observable o, List<?> changes, boolean addedOrChanged) throws IncorrectObjectTypeException {

        try {
            if(addedOrChanged){
                fbg.pushMessages((List<Message>) changes);
            }
            else {
                fbg.removeMessages((List<Message>) changes);
            }

        } catch(ClassCastException e) {
            throw new IncorrectObjectTypeException("List of type Message required");
        }

    }
}
