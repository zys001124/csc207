package observers;

import exceptions.IncorrectObjectTypeException;

import java.util.ArrayList;
import java.util.List;

/**
 * An object that can be observed
 */
public class Observable {
    private final List<Observer> observers;

    /**
     * The constructor for an Observable
     * <p>
     * Creates Observer list object
     */
    public Observable() {
        observers = new ArrayList<>();
    }

    /**
     * Adds an Observer to this Observable object
     *
     * @param o - the Observer to add
     */
    public void addObserver(Observer o) {
        observers.add(o);
    }

    /**
     * Deletes an Observer to this Observable object
     *
     * @param o - the Observer to delete
     */
    public void deleteObserver(Observer o) {
        observers.remove(o);
    }

    /**
     * Deletes all Observers of this Observable object
     */
    public void deleteObservers() {
        observers.removeAll(observers);
    }

    /**
     * Notifies all observers of a change
     *
     * @param changes        - The list of objects that have been changed (added/changed/removed)
     * @param addedOrChanged - Whether or not these changes are either new additions or changes of existing objects
     */
    public void notifyObservers(List<?> changes, boolean addedOrChanged) {

        try {
            for (Observer o : observers) {
                o.update(this, changes, addedOrChanged);
            }
        } catch (IncorrectObjectTypeException e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }
}
