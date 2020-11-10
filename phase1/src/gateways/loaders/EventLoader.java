package gateways.loaders;

import entities.Event;
import exceptions.IncorrectNumberOfParametersException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class EventLoader extends Loader<Event> {

    @Override
    public Event createInstance(String[] parameters) {

        if(parameters.length != 7){
            throw new IncorrectNumberOfParametersException();
        }

        return new Event(parameters[0], LocalDateTime.parse(parameters[1]),
                UUID.fromString(parameters[2]), UUID.fromString(parameters[3]),
                UUID.fromString(parameters[4]), new ArrayList<>(), Integer.getInteger(parameters[6]));
    }
}
