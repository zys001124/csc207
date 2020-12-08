package gateways.snapshotreaders;

import com.google.firebase.database.DataSnapshot;
import entities.User;

/**
 * A DataSnapShotReader that can parse User objects from DataSnapshots
 */
public class UserDataSnapshotReader implements DataSnapshotReader<User> {

    /**
     * gets the data snapshot from the firebase database
     *
     * @param dataSnapshot the data snapshot to be passed
     * @return a User from the data snapshot
     */
    @Override
    public User getFromDataSnapshot(DataSnapshot dataSnapshot) {
        User.UserData userData = dataSnapshot.getValue(User.UserData.class);
        return User.fromUserData(userData);
    }
}
