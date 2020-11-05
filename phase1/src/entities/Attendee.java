package entities;

import java.util.UUID;

public class Attendee {

    private UUID attendeeId;
    private String username;
    private String password;

    public Attendee(String username, String password) {
        this.username = username;
        this.password = password;
        attendeeId = UUID.randomUUID();
    }

    public String getUsername(){return username;}

    public final UUID getId(){return attendeeId;}

    public boolean verifyLogin(String usr, String pass) {
        return usr.equals(username) && pass.equals(password);
    }
}
