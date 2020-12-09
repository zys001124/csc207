package gateways.snapshotreaders;

import com.google.firebase.database.DataSnapshot;

/**
 * An interface for classes that wish to to convert information in
 * DataSnapshot objects to objects of type T to implement
 *
 * @param <T> The type of object DataSnapshot information will be used to make
 */
public interface DataSnapshotReader<T> {
    /**
     * Gets an instance of T from a DataSnapshot
     *
     * @param dataSnapshot - the DataSnapshot to be read
     * @return An object of type T
     */
    T getFromDataSnapshot(DataSnapshot dataSnapshot);
}
