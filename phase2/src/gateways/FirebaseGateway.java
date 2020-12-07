package gateways;

import com.google.firebase.database.*;

import java.util.List;

public abstract class FirebaseGateway<T> {

    private final FirebaseDatabase database;
    protected DatabaseReference databaseReference;

    protected FirebaseGateway(String referencePath) {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child(referencePath);
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

    public abstract void pushEntities(List<T> entities);

    public abstract void removeEntities(List<T> entities);

    protected abstract void onChildAdded(DataSnapshot dataSnapshot, String s);

    protected abstract void onChildChanged(DataSnapshot dataSnapshot, String s);

    protected abstract void onChildRemoved(DataSnapshot dataSnapshot);

    protected abstract void onChildMoved(DataSnapshot dataSnapshot, String s);

    protected abstract void onCancelled(DatabaseError databaseError);
}
