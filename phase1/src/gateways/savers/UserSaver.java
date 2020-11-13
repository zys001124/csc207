package gateways.savers;

import entities.User;

import java.io.IOException;

public class UserSaver extends Saver<User> {

    public UserSaver(String outputFileName) throws IOException {
        super(outputFileName);
    }

    public void save(User attendee) throws IOException {
        output.append(attendee.getUsername());
        output.append(parameterSeparationChar);
        output.append(attendee.getPassword());
        output.append(parameterSeparationChar);
        output.append(attendee.getId().toString());
        output.append(parameterSeparationChar);
        if(attendee.getType() == User.UserType.ORGANIZER) {
            output.append("O");
        }
        else if(attendee.getType() == User.UserType.ATTENDEE) {
            output.append("A");
        }
        else {
            output.append("S");
        }
        output.append("\n");

        output.flush();
    }

}
