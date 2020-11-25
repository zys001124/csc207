package gateways.loaders;

import entities.User;
import exceptions.IncorrectNumberOfParametersException;

import java.util.UUID;

/**
 * A Loader for User objects
 */
public class UserLoader extends Loader<User> {

    /**
     * Creates a User object with the given parameters
     *
     * @param parameters parameters for creating an object of type T as strings
     * @return a User object
     */
    @Override
    public User createInstance(String[] parameters) {
        if (parameters.length == 4) {
            if (parameters[3].equals("O")) {
                return new User(User.UserType.ORGANIZER, parameters[0], parameters[1], UUID.fromString(parameters[2]));
            } else if (parameters[3].equals("A")) {
                return new User(User.UserType.ATTENDEE, parameters[0], parameters[1], UUID.fromString(parameters[2]));
            }else if (parameters[3].equals("V")) {
                    return new User(User.UserType.VIP, parameters[0], parameters[1], UUID.fromString(parameters[2]));
            }else if (parameters[3].equals("D")) {
                return new User(User.UserType.ADMIN, parameters[0], parameters[1], UUID.fromString(parameters[2]));
            }else {
                return new User(User.UserType.SPEAKER, parameters[0], parameters[1], UUID.fromString(parameters[2]));
            }
        } else {
            throw new IncorrectNumberOfParametersException();
        }
    }
}
