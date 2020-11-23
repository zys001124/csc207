package gateways.loaders;

import entities.Event;
import exceptions.IncorrectNumberOfParametersException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A Loader for Event objects
 */
public class EventLoader extends Loader<Event> {

    /**
     * Creates an Event object with the given parameters
     *
     * @param parameters parameters for creating an object of type T as strings
     * @return an Event object
     */
    @Override
    public Event createInstance(String[] parameters) {

        if (parameters.length < 6 && parameters.length != 0) {
            throw new IncorrectNumberOfParametersException();
        }

        List<UUID> attendees = new ArrayList<>();
        for (int i = 5; i < parameters.length - 3; i++) {
            attendees.add(UUID.fromString(parameters[i]));
        }

        return new Event(parameters[0],
                LocalDateTime.parse(parameters[1]),
                UUID.fromString(parameters[2]),
                UUID.fromString(parameters[3]),
                UUID.fromString(parameters[4]),
                attendees,
                Integer.parseInt(parameters[parameters.length - 3]),
                Integer.parseInt(parameters[parameters.length - 2]),
                parameters[parameters.length - 1]);
    }
}
