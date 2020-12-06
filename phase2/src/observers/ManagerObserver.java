package observers;

import exceptions.IncorrectObjectTypeException;
import gateways.FirebaseGateway;

import java.util.List;

public class ManagerObserver<T> implements Observer<T> {

    private FirebaseGateway<T> gateway;

    public ManagerObserver(FirebaseGateway<T> gateway) {
        this.gateway = gateway;
    }

    @Override
    public void update(Observable o, List<T> changes, boolean addedOrChanged, boolean retrievedFromDataBase) throws IncorrectObjectTypeException {
        if(retrievedFromDataBase){return;}
        try {
            if(addedOrChanged){
                gateway.pushEntities(changes);
            }
            else {
                gateway.removeEntities(changes);
            }

        } catch(ClassCastException e) {
            throw new IncorrectObjectTypeException("List of type Message required");
        }

    }
}
