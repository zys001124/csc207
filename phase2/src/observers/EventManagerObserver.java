//package observers;
//
//import entities.Event;
//import exceptions.IncorrectObjectTypeException;
//import gateways.EventGateway;
//
//import java.util.List;
//
//public class EventManagerObserver implements Observer {
//
//    private EventGateway eventGateway;
//
//    public EventManagerObserver(EventGateway gateway) {
//        eventGateway = gateway;
//    }
//
//    @Override
//    public void update(Observable o, List<?> changes, boolean addedOrChanged, boolean retrievedFromDataBase) throws IncorrectObjectTypeException {
//        if(retrievedFromDataBase){return;}
//        try {
//            if(addedOrChanged){
//                eventGateway.pushEvents((List<Event>) changes);
//            }
//            else {
//                eventGateway.removeEvents((List<Event>) changes);
//            }
//
//        } catch(ClassCastException e) {
//            throw new IncorrectObjectTypeException("List of type Message required");
//        }
//    }
//}
