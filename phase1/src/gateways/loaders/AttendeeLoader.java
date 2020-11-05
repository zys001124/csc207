package gateways.loaders;

import entities.Attendee;
import entities.Organizer;
import exceptions.IncorrectNumberOfParametersException;

import java.util.UUID;

public class AttendeeLoader extends Loader<Attendee> {

    @Override
    public Attendee createInstance(String[] parameters) {
        if(parameters.length == 4){
            if(parameters[3].equals("O"))
            {
                return new Organizer(parameters[0], parameters[1], UUID.fromString(parameters[2]));
            }
            else
            {
                return new Attendee(parameters[0], parameters[1], UUID.fromString(parameters[2]));
            }
        }
        else {
            throw new IncorrectNumberOfParametersException();
        }
    }
}
