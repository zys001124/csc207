package entities;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {

    private UUID id;
    private String username;
    private String password;
    private UserType type;

    public User(UserType type, String username, String password, UUID id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    public final String getUsername(){return username;}

    public final UUID getId(){return id;}

    public final UserType getType() {return type;}

    public boolean verifyPassword(String pass) {
        return pass.equals(password);
    }

    // TODO Remove this once we can serialize objects
    public final String getPassword() {return password;}

    public enum UserType {
        ATTENDEE,
        ORGANIZER,
        SPEAKER
    }

}
