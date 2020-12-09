package observers;

import exceptions.IncorrectObjectTypeException;
import gateways.FirebaseGateway;

import java.util.List;

/**
 * An observer that will query a FirebaseGateway when changes are made
 *
 * @param <T> The type of entity additions/removals/changes are being made to
 */
public class UpdateDatabaseObserver<T> implements Observer<T> {

    private final FirebaseGateway<T> gateway;

    /**
     * The constructor for UpdateDatabaseObserver
     *
     * @param gateway - the FirebaseGateway to be queried
     */
    public UpdateDatabaseObserver(FirebaseGateway<T> gateway) {
        this.gateway = gateway;
    }

    /**
     * Called when this observer is notified
     *
     * @param o              - The object that is being observed
     * @param changes        - the List of changes that were made
     * @param addedOrChanged - whether or not the changes were either additions or changes
     *                       within the objects
     * @throws IncorrectObjectTypeException
     */
    @Override
    public void update(Observable o, List<T> changes, boolean addedOrChanged) throws IncorrectObjectTypeException {
        try {
            if (addedOrChanged) {
                gateway.pushEntities(changes);
            } else {
                gateway.removeEntities(changes);
            }

        } catch (ClassCastException e) {
            throw new IncorrectObjectTypeException("List of type Message required");
        }

    }
}
