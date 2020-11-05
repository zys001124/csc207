package gateways.loaders;

import entities.Event;
import exceptions.IncorrectNumberOfParametersException;

import java.time.LocalDateTime;
import java.util.UUID;

public class EventLoader extends Loader<Event> {

    @Override
    public Event createInstance(String[] parameters) {

        if(parameters.length != 3){
            throw new IncorrectNumberOfParametersException();
        }

        return new Event(parameters[0], LocalDateTime.parse(parameters[1]),
                UUID.fromString(parameters[2]));
    }
}
