package observers;

import exceptions.IncorrectObjectTypeException;

import java.util.List;

public interface Observer<T> {

    void update(Observable o, List<T> changes, boolean addedOrChanged, boolean retrievedFromDatabase) throws IncorrectObjectTypeException;
}
