package gateways.loaders;

import entities.User;
import exceptions.IncorrectNumberOfParametersException;

import java.util.UUID;

public class UserLoader extends Loader<User> {

    @Override
    public User createInstance(String[] parameters) {
        if(parameters.length == 4){
            if(parameters[3].equals("O"))
            {
                return new User(User.UserType.ORGANIZER, parameters[0], parameters[1], UUID.fromString(parameters[2]));
            }
            else if(parameters[3].equals("A"))
            {
                return new User(User.UserType.ATTENDEE, parameters[0], parameters[1], UUID.fromString(parameters[2]));
            }
            else
            {
                return new User(User.UserType.SPEAKER, parameters[0], parameters[1], UUID.fromString(parameters[2]));
            }
        }
        else {
            throw new IncorrectNumberOfParametersException();
        }
    }
}
