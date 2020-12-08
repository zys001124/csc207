package gateways;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import entities.User;
import useCaseClasses.UserManager;

import java.util.List;

/**
 * A gateway for handling the saving/loading of User data from
 * a Firebase Realtime Database
 */
public class UserGateway extends FirebaseGateway<User> {

    public UserManager userManager;

    /**
     * The constructor for an UserGateway object
     *
     * @param mm       - The UserManager the program is using
     * @param database - A FirebaseDatabase object that will be used to create
     *                 a reference to the directory in the database holding
     *                 User information
     */
    public UserGateway(UserManager um, FirebaseDatabase database) {
        super("Users", database);
        userManager = um;
    }

    /**
     * This method pushes the information of a list of Users to
     * the Firebase Realtime Database
     * @param users - the list of Users to save information of
     */
    @Override
    public void pushEntities(List<User> users) {
        for (User user : users) {
            databaseReference.child(user.getUsername()).setValueAsync(user.getUserData());
        }
    }

    /**
     * This method removes the information of a list of Users to
     * the Firebase Realtime Database
     * @param users - the list of Users to remove information of
     */
    @Override
    public void removeEntities(List<User> users) {
        for (User user : users) {
            databaseReference.child(user.getUsername()).removeValueAsync();
        }
    }

    /**
     * This method is called when a piece of information is added under
     * the directory in the database that holds User information
     * @param dataSnapshot - an object containing the information that has been added
     * @param s - the name of the node before being added (it will be null since the node didnt
     *          exist before)
     */
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        userManager.addUserFromDataSnapshot(dataSnapshot);
    }

    /**
     * This method is called when a piece of information is changed under
     * the directory in the database that holds User information
     * @param dataSnapshot - an object containing the information that has been changed
     * @param s - the name of the node before being added
     */
    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        userManager.changeUserFromDataSnapshot(dataSnapshot);
    }

    /**
     * This method is called when a piece of information is removed under
     * the directory in the database that holds User information
     * @param dataSnapshot - an object containing the information that has been removed
     */
    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        userManager.removeUserFromDataSnapshot(dataSnapshot);
    }
}
