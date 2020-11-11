package gateways.loaders;

import entities.Event;
import exceptions.IncorrectNumberOfParametersException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventLoader extends Loader<Event> {

    @Override
    public Event createInstance(String[] parameters) {

        if(parameters.length < 7 && parameters.length != 0){
            throw new IncorrectNumberOfParametersException();
        }

        List<UUID> attendees = new ArrayList<>();
        for(int i = 5; i<parameters.length-1; i++) {
            attendees.add(UUID.fromString(parameters[i]));
        }

        return new Event(parameters[0],LocalDateTime.parse(parameters[1]),
                UUID.fromString(parameters[2]), UUID.fromString(parameters[3]),
                UUID.fromString(parameters[4]), attendees,
                Integer.parseInt(parameters[parameters.length-1]));
    }
}
