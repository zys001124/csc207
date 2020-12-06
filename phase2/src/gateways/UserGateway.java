package gateways;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import entities.User;
import useCaseClasses.UserManager;

import java.util.List;
import java.util.UUID;

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
        User.UserData userData = dataSnapshot.getValue(User.UserData.class);
        userManager.addUserFromDatabase(userData);
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        User.UserData userData = dataSnapshot.getValue(User.UserData.class);
        userManager.changeUserFromDatabase(userData);
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        User.UserData userData = dataSnapshot.getValue(User.UserData.class);
        userManager.removeUser(UUID.fromString(userData.uuid), true);
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
