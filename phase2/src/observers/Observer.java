package observers;

import exceptions.IncorrectObjectTypeException;

import java.util.List;

public interface Observer {

    public void update(Observable o, List<?> changes, boolean addedOrChanged) throws IncorrectObjectTypeException;
}
