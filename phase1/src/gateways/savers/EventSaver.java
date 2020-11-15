package gateways.savers;

import entities.Event;

import java.io.IOException;
import java.util.UUID;

/**
 * A Saver for Event objects
 */
public class EventSaver extends Saver<Event> {

    /**
     * Creates an EventSaver object that will save Events at the
     * given filepath
     *
     * @param outputFileName - the filepath to save Event objects
     * @throws IOException if the file does not exist at the filepath
     */
    public EventSaver(String outputFileName) throws IOException {
        super(outputFileName);
    }

    /**
     * Saves an individual Event
     *
     * @param event - The Event to be saved
     * @throws IOException if the file does not exist at the filepath
     */
    public void save(Event event) throws IOException {
        output.append(event.getEventTitle());
        output.append(parameterSeparationChar);
        output.append(event.getEventTime().toString());
        output.append(parameterSeparationChar);
        output.append(event.getId().toString());
        output.append(parameterSeparationChar);
        output.append(event.getOrganizerId().toString());
        output.append(parameterSeparationChar);
        output.append(event.getSpeakerId().toString());
        output.append(parameterSeparationChar);
        for (UUID attendeeId : event) {
            output.append(attendeeId.toString());
            output.append(parameterSeparationChar);
        }
        output.append(event.getEventRoom() + "");
        output.append("\n");

        output.flush();
    }
}
