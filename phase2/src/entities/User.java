package entities;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


/**
 * Represents a User in the conference system
 */
public class User implements Serializable {

    private final UUID id;
    private final String username;
    private final String password;
    private final UserType type;

    /**
     * Creates a user with the specified UserType, username, password, and UUID
     *
     * @param type     - the Users type
     * @param username - the Users username
     * @param password - the Users password
     * @param id       - the Users UUID
     */
    public User(UserType type, String username, String password, UUID id) {
        this.username = username;
        this.password = password;
        this.id = id;
        this.type = type;
    }

    /**
     * Builds a user object from UserData
     *
     * @param data - The UserData to construct the user from
     * @return A User object - The user constructed from the data
     */
    public static User fromUserData(UserData data) {
        return new User(UserType.valueOf(data.type), data.username,
                data.password, UUID.fromString(data.uuid));
    }

    /**
     * Gets the Users username
     *
     * @return a string that is the Users username
     */
    public final String getUsername() {
        return username;
    }

    /**
     * Gets the Users UUID
     *
     * @return a UUID object - the users specific UUID
     */
    public final UUID getId() {
        return id;
    }

    /**
     * Gets the users UserType
     *
     * @return the UserType enum that is the Users type
     */
    public final UserType getType() {
        return type;
    }

    /**
     * Verify's that the String passed in as a parameter is the same
     * as the Users password
     *
     * @param pass the password that will be checked for correctness
     * @return a boolean value representing whether or not <pass> is
     * the correct password
     */
    public boolean verifyPassword(String pass) {
        return pass.equals(password);
    }

    /**
     * Returns the information of this User object packaged
     * as UserData
     *
     * @return the UserData of this User
     */
    public UserData getUserData() {
        UserData data = new UserData();

        data.uuid = id.toString();
        data.password = password;
        data.username = username;
        data.type = type.toString();

        return data;
    }

    /**
     * Checks content equality
     *
     * @param o - the object we wish to compare
     * @return Whether or not this and o are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                type == user.type;
    }

    /**
     * Hashes the information of this user
     *
     * @return the hash int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, type);
    }

    /**
     * This documents what kind of User the User is
     */
    public enum UserType {
        ATTENDEE,
        ORGANIZER,
        SPEAKER,
        VIP,
        ADMIN
    }

    /**
     * The User Data for each user
     */
    public static class UserData {
        public String uuid;
        public String username;
        public String password;
        public String type;
    }

}
