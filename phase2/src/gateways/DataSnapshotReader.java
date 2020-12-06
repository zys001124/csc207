package gateways;

import com.google.firebase.database.DataSnapshot;

public interface DataSnapshotReader<T>
{
    T getFromDataSnapshot(DataSnapshot dataSnapshot);
}
