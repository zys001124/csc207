package gateways.savers;

import entities.Attendee;
import entities.Organizer;

import java.io.IOException;

public class AttendeeSaver extends Saver<Attendee> {

    public AttendeeSaver(String outputFileName) throws IOException {
        super(outputFileName);
    }

    public void save(Attendee attendee) throws IOException {
        output.append(attendee.getUsername());
        output.append(",");
        output.append(attendee.getPassword());
        output.append(",");
        output.append(attendee.getId().toString());
        output.append(",");
        if(attendee instanceof Organizer) {
            output.append("O");
        }
        else {
            output.append("A");
        }
        output.append("\n");

        output.flush();
    }

}
