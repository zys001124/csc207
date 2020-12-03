package observers;

import entities.Message;
import entities.User;
import exceptions.IncorrectObjectTypeException;
import gateways.FirebaseGateway;

import java.util.List;

public class UserManagerObserver implements Observer {

    private FirebaseGateway fbg;

    public UserManagerObserver(FirebaseGateway gateway) {
        fbg = gateway;
    }

    @Override
    public void update(Observable o, List<?> changes, boolean addedOrChanged, boolean retrievedFromDataBase) throws IncorrectObjectTypeException {
        if(retrievedFromDataBase){return;}
        try {
            if(addedOrChanged){
                fbg.pushUsers((List<User>) changes);
            }
            else {
                fbg.removeUsers((List<User>) changes);
            }

        } catch(ClassCastException e) {
            throw new IncorrectObjectTypeException("List of type Message required");
        }

    }
}
