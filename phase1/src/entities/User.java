package entities;

import java.io.Serializable;
import java.util.UUID;


/**
 * Represents a User in the conference system
 */
public class User implements Serializable {

    private UUID id;
    private String username;
    private String password;
    private UserType type;

    /**
     * Creates a user with the specified UserType, username, password, and UUID
     *
     * @param type - the Users type
     * @param username - the Users username
     * @param password - the Users password
     * @param id - the Users UUID
     */
    public User(UserType type, String username, String password, UUID id) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.type = type;
    }

    /**
     * Gets the Users username
     * @return a string that is the Users username
     */
    public final String getUsername(){return username;}

    /**
     * Gets the Users UUID
     * @return a UUID object - the users specific UUID
     */
    public final UUID getId(){return id;}

    /**
     * Gets the users UserType
     * @return the UserType enum that is the Users type
     */
    public final UserType getType() {return type;}

    /**
     * Verify's that the String passed in as a parameter is the same
     * as the Users password
     * @param pass the password that will be checked for correctness
     * @return a boolean value representing whether or not <pass> is
     * the correct password
     */
    public boolean verifyPassword(String pass) {
        return pass.equals(password);
    }

    // TODO Remove this once we can serialize objects

    /**
     * Gets the Users password
     * @return a String - the Users password
     */
    public final String getPassword() {return password;}

    /**
     * This documents what kind of User the User is
     */
    public enum UserType {
        ATTENDEE,
        ORGANIZER,
        SPEAKER
    }

}
