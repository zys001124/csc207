//package observers;
//
//import entities.User;
//import exceptions.IncorrectObjectTypeException;
//import gateways.UserGateway;
//
//import java.util.List;
//
//public class UserManagerObserver implements Observer {
//
//    private UserGateway userGateway;
//
//    public UserManagerObserver(UserGateway gateway) {
//        userGateway = gateway;
//    }
//
//    @Override
//    public void update(Observable o, List<?> changes, boolean addedOrChanged, boolean retrievedFromDataBase) throws IncorrectObjectTypeException {
//        if(retrievedFromDataBase){return;}
//        try {
//            if(addedOrChanged){
//                userGateway.pushUsers((List<User>) changes);
//            }
//            else {
//                userGateway.removeUsers((List<User>) changes);
//            }
//
//        } catch(ClassCastException e) {
//            throw new IncorrectObjectTypeException("List of type Message required");
//        }
//
//    }
//}
