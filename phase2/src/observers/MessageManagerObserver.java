//package observers;
//
//import entities.Message;
//import exceptions.IncorrectObjectTypeException;
//import gateways.MessageGateway;
//
//import java.util.List;
//
//public class MessageManagerObserver implements Observer {
//
//    private MessageGateway messageGateway;
//
//    public MessageManagerObserver(MessageGateway gateway) {
//        messageGateway = gateway;
//    }
//
//    @Override
//    public void update(Observable o, List<?> changes, boolean addedOrChanged, boolean retrievedFromDataBase) throws IncorrectObjectTypeException {
//        if(retrievedFromDataBase){return;}
//        try {
//            if(addedOrChanged){
//                messageGateway.pushMessages((List<Message>) changes);
//            }
//            else {
//                messageGateway.removeMessages((List<Message>) changes);
//            }
//
//        } catch(ClassCastException e) {
//            throw new IncorrectObjectTypeException("List of type Message required");
//        }
//
//    }
//}
