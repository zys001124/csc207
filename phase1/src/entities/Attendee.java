package entities;

import java.io.Serializable;
import java.util.UUID;

public class Attendee implements Serializable {

    private UUID attendeeId;
    private String username;
    private String password;

    public Attendee(String username, String password, UUID id) {
        this.username = username;
        this.password = password;
        attendeeId = id;
    }

    public String getUsername(){return username;}

    public final UUID getId(){return attendeeId;}

    public boolean verifyLogin(String usr, String pass) {
        return usr.equals(username) && pass.equals(password);
    }

    // TODO Remove this once we can serialize objects
    public String getPassword() {return password;}
}
