package observers;

import exceptions.IncorrectObjectTypeException;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private final List<Observer> observers;

    public Observable() {
        observers = new ArrayList<>();
    }

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void deleteObserver(Observer o) {
        observers.remove(o);
    }

    public void deleteObservers() {
        observers.removeAll(observers);
    }

    public void notifyObservers(List<?> changes, boolean addedOrChanged, boolean retrievedFromDataBase) {

        try {
            for (Observer o : observers) {
                o.update(this, changes, addedOrChanged, retrievedFromDataBase);
            }
        } catch (IncorrectObjectTypeException e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }
}
