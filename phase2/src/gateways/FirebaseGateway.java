package gateways;

import com.google.firebase.database.*;
import gateways.snapshotreaders.DataSnapshotReader;

import java.util.List;

/**
 * A Gateway object for handling the saving/loading of object data from
 * a Firebase Realtime Database
 *
 * @param <T> - The type of object being saved/loaded
 */
public abstract class FirebaseGateway<T> {

    private final FirebaseDatabase database;
    protected DatabaseReference databaseReference;
    protected DataSnapshotReader<T> snapshotReader;

    /**
     * Constructor for a FirebaseGateway object
     *
     * @param referencePath - A string representing the path in the database that
     *                      this gateway should save/load information from
     * @param database      - A FirebaseDatabase object to get references from
     */
    protected FirebaseGateway(String referencePath, FirebaseDatabase database) {
        this.database = database;
        databaseReference = database.getReference().child(referencePath);
        databaseReference.keepSynced(true);
        snapshotReader = getSnapshotReader();
        addListeners();
    }

    private void addListeners() {
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseGateway.this.onChildAdded(dataSnapshot, s);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                FirebaseGateway.this.onChildChanged(dataSnapshot, s);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                FirebaseGateway.this.onChildRemoved(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                FirebaseGateway.this.onChildMoved(dataSnapshot, s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                FirebaseGateway.this.onCancelled(databaseError);
            }
        });
    }

    /**
     * This method pushes the information of a list of objects to
     * the Firebase Realtime Database
     *
     * @param entities - the list of objects to save information of
     */
    public abstract void pushEntities(List<T> entities);

    /**
     * This method removes the information of a list of objects to
     * the Firebase Realtime Database
     *
     * @param entities - the list of objects to remove information of
     */
    public abstract void removeEntities(List<T> entities);

    /**
     * This method is called when a piece of information is added under
     * the directory in the database specified by <referencePath> in the constructor
     *
     * @param dataSnapshot - an object containing the information that has been added
     * @param s            - the name of the node before being added (it will be null since the node didnt
     *                     exist before)
     */
    protected abstract void onChildAdded(DataSnapshot dataSnapshot, String s);

    /**
     * This method is called when a piece of information is changed under
     * the directory in the database specified by <referencePath> in the constructor
     *
     * @param dataSnapshot - an object containing the information that has been changed
     * @param s            - the name of the node before being added
     */
    protected abstract void onChildChanged(DataSnapshot dataSnapshot, String s);

    /**
     * This method is called when a piece of information is removed under
     * the directory in the database specified by <referencePath> in the constructor
     *
     * @param dataSnapshot - an object containing the information that has been removed
     */
    protected abstract void onChildRemoved(DataSnapshot dataSnapshot);

    /**
     * This method is called when a piece of information is moved under
     * the directory in the database specified by <referencePath> in the constructor
     *
     * @param dataSnapshot - an object containing the information that has been moved
     * @param s            - the name of the node before being moved
     */
    protected void onChildMoved(DataSnapshot dataSnapshot, String s) {
    }

    /**
     * This method is called when a push or retrieval from the Firebase
     * Realtime Database has been cancelled
     *
     * @param databaseError - the error that caused the exchange of data to be
     *                      cancelled
     */
    protected void onCancelled(DatabaseError databaseError) {
    }

    /**
     * Gets the snapshotReader from a subclass
     *
     * @return a DataSnapshotReader
     */
    protected abstract DataSnapshotReader<T> getSnapshotReader();
}
