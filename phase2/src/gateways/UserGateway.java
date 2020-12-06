package gateways;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import entities.User;
import useCaseClasses.UserManager;

import java.util.List;

public class UserGateway extends FirebaseGateway<User> {

    public UserManager userManager;

    public UserGateway(UserManager um) {
        super("Users");
        userManager = um;
    }

    @Override
    public void pushEntities(List<User> entities) {
        for (User user : entities) {
            databaseReference.child(user.getUsername()).setValueAsync(user.getUserData());
        }
    }

    @Override
    public void removeEntities(List<User> entities) {
        for (User user : entities) {
            databaseReference.child(user.getUsername()).removeValueAsync();
        }
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        userManager.addUserFromDataSnapshot(dataSnapshot);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        userManager.changeUserFromDataSnapshot(dataSnapshot);
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        userManager.removeUserFromDataSnapshot(dataSnapshot);
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
