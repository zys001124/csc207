package observers;

import exceptions.IncorrectObjectTypeException;

import java.util.List;

/**
 * An interface for classes that which to observe other objects to implement
 *
 * @param <T> They type of object that gets changed (Not necessarily the observable)
 */
public interface Observer<T> {

    /**
     * Called when this observer is notified
     *
     * @param o              - The object that is being observed
     * @param changes        - the List of changes that were made
     * @param addedOrChanged - whether or not the changes were either additions or changes
     *                       within the objects
     * @throws IncorrectObjectTypeException -if object type does not match
     */
    void update(Observable o, List<T> changes, boolean addedOrChanged) throws IncorrectObjectTypeException;
}
