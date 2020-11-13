package gateways.savers;

import entities.Event;

import java.io.IOException;
import java.util.UUID;

public class EventSaver extends Saver<Event>{

    public EventSaver(String outputFileName) throws IOException {
        super(outputFileName);
    }

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
        for(UUID attendeeId: event) {
            output.append(attendeeId.toString());
            output.append(parameterSeparationChar);
        }
        output.append(event.getEventRoom()+"");
        output.append("\n");

        output.flush();
    }
}
