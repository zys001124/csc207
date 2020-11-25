package gateways.savers;

import entities.User;

import java.io.IOException;

/**
 * A Saver for User objects
 */
public class UserSaver extends Saver<User> {

    /**
     * Creates a UserSaver object that will save Users at the
     * given filepath
     *
     * @param outputFileName - the filepath to save User objects
     * @throws IOException if the file does not exist at the filepath
     */
    public UserSaver(String outputFileName) throws IOException {
        super(outputFileName);
    }

    /**
     * Saves an individual User
     *
     * @param user - The User to be saved
     * @throws IOException if the file does not exist at the filepath
     */
    public void save(User user) throws IOException {
        output.append(user.getUsername());
        output.append(parameterSeparationChar);
        output.append(user.getPassword());
        output.append(parameterSeparationChar);
        output.append(user.getId().toString());
        output.append(parameterSeparationChar);
        if (user.getType() == User.UserType.ORGANIZER) {
            output.append("O");
        } else if (user.getType() == User.UserType.ATTENDEE) {
            output.append("A");
        }  else if (user.getType() == User.UserType.VIP) {
            output.append("V");
        }
        else if (user.getType() == User.UserType.ADMIN) {
            output.append("D");
        } else {
            output.append("S");
        }
        output.append("\n");

        output.flush();
    }

}
