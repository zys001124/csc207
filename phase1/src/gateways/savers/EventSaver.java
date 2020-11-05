package gateways.savers;

import entities.Event;

import java.io.IOException;

public class EventSaver extends Saver<Event>{

    public EventSaver(String outputFileName) throws IOException {
        super(outputFileName);
    }

    public void save(Event event) throws IOException {
        output.append(event.getEventTitle());
        output.append(",");
        output.append(event.getEventTime().toString());
        output.append(",");
        output.append(event.getId().toString());
        output.append(",");
        output.append("\n");

        output.flush();
    }
}
