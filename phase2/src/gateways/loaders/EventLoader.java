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

        if (parameters.length < 9 && parameters.length != 0) {
            throw new IncorrectNumberOfParametersException();
        }


        ArrayList<UUID> speakers = new ArrayList<>();
        int i = 5;


        while(!parameters[i].equals("endSpeaker")) {
            speakers.add(UUID.fromString(parameters[i]));
            i++;
        }

        i++;

        List<UUID> attendees = new ArrayList<>();
        while(i < parameters.length - 3){
            attendees.add(UUID.fromString(parameters[i]));
            i++;
        }


        return new Event(parameters[0],
                LocalDateTime.parse(parameters[1]),
                LocalDateTime.parse(parameters[2]),
                UUID.fromString(parameters[3]),
                UUID.fromString(parameters[4]),
                speakers,
                attendees,
                Integer.parseInt(parameters[parameters.length - 3]),
                Integer.parseInt(parameters[parameters.length - 2]),
                Boolean.parseBoolean(parameters[parameters.length - 1]));
    }
}
