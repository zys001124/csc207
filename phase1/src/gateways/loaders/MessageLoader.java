package gateways.loaders;

import entities.Message;
import exceptions.IncorrectNumberOfParametersException;

import java.util.UUID;

public class MessageLoader extends Loader<Message> {

    @Override
    public Message createInstance(String[] parameters) {

        if(parameters.length != 4){
            throw new IncorrectNumberOfParametersException();
        }

        return new Message(parameters[0], UUID.fromString(parameters[1]),
                UUID.fromString(parameters[2]), UUID.fromString(parameters[3]));
    }
}
