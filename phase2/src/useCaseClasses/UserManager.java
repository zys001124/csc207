package useCaseClasses;

import entities.User;
import exceptions.*;
import observers.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A manager for managing all the users in the tech conference
 */
public class UserManager extends Observable {

    private final List<User> users;

    private User currentlyLoggedIn;

    /**
     * Creates a UserManager with no users
     */
    public UserManager() {
        users = new ArrayList<>();
    }

    /**
     * finds a user in the list of users in the conference system and returns that user.
     * returns its UUID or null if the user is not found.
     *
     * @param username String of the username that will be used to find the user in the System
     * @return the User's UUID to the corresponding username or null if user does not exist
     */
    public UUID getUserID(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u.getId();
            }
        }
        return null;
    }

    /**
     * gets the user based on a given UUID. returns null if not found
     *
     * @param id the UUID of the user to be found
     * @return either the user that is found or null if user does not exist
     */
    public User getUser(UUID id) {

        for (User u : users) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    /**
     * finds a user in the list of users in the conference system and returns that user.
     * throws a UserNotFoundException if that username can not be found in the list
     *
     * @param username String of the username that will be used to find the user in the System
     * @return the User to the corresponding username
     * @throws UserNotFoundException for when the user can not be found
     */
    public User getUser(String username) throws UserNotFoundException {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        throw new UserNotFoundException(username);
    }

    /**
     * removes an user in the given event list where the event is given by its UUID
     * returns null if event can't be found.
     *
     * @param id he UUID of the user to be found
     * @return the user that is being removed or null if it can't be found.
     */
    public User removeUser(UUID id) {
        List<User> usersToRemove = new ArrayList<>();
        for (User user : users) {
            int index = users.indexOf(user);
            if (user.getId().equals(id)) {
                usersToRemove.add(users.remove(index));
                notifyObservers(usersToRemove, false);
                return usersToRemove.get(0);
            }
        }
        return null;
    }

    /**
     * Removes a user from the stored list of Users
     *
     * @param user - The user to remove
     * @return the User object that was removed
     */
    public User removeUser(User user) {
        List<User> usersToRemove = new ArrayList<>();
        if (users.remove(user)) {
            usersToRemove.add(user);
            notifyObservers(usersToRemove, false);
            return usersToRemove.get(0);
        } else {
            return null;
        }
    }

    /**
     * Method that removes the user given a data snapshot that is taken in from firebase for live
     * updates
     */
    public void removeUserFromDatabase(User user) {
        UUID userId = user.getId();
        List<User> usersToRemove = new ArrayList<>();
        for (User u : users) {
            int index = users.indexOf(user);
            if (user.getId().equals(userId)) {
                usersToRemove.add(users.remove(index));
                notifyObservers(usersToRemove, false);
            }
        }
    }

    /**
     * helper method to see which user is currently logged in.
     *
     * @return user that is currently logged in
     */
    public User getCurrentlyLoggedIn() {
        return currentlyLoggedIn;
    }

    /**
     * adds a user to the list of users
     *
     * @param type     the role of the user to be added
     * @param username the username of the user to be added
     * @param password the password of the user to be added
     * @throws UsernameAlreadyExistsException - if the username is already seen in the system
     */
    public void addUser(User.UserType type, String username, String password) throws UsernameAlreadyExistsException,
            NoPasswordException, NoUsernameException {
        if (doesUserExist(username)) {
            throw new UsernameAlreadyExistsException("Username: " + username + " is taken");
        }
        if (username.equals("")) {
            throw new NoUsernameException("Username not given.");
        }
        if (password.equals("")) {
            throw new NoPasswordException("Password not given.");
        }
        User user = new User(type, username, password, UUID.randomUUID());
        List<User> usersToAdd = new ArrayList<>();
        usersToAdd.add(user);
        users.addAll(usersToAdd);
        notifyObservers(usersToAdd, true);
    }

    /**
     * Adds new users from a data snapshot that is linked to firebase for live updates
     */
    public void addUser(User user) {
        List<User> usersToAdd = new ArrayList<>();
        if (!doesUserExist(user.getUsername())) {
            usersToAdd.add(user);
            users.addAll(usersToAdd);
            notifyObservers(usersToAdd, true);
        }
    }

    /**
     * Changes the user from the data snapshot that is given which is linked to firebase
     */
    public void changeUser(User user) {
        List<User> usersToAdd = new ArrayList<>();
        usersToAdd.add(user);
        removeUserFromDatabase(user);
        users.addAll(usersToAdd);
        notifyObservers(usersToAdd, true);
    }

    /**
     * helper method to check and see if the username exists
     * called in addUser
     *
     * @param username the string of the username to see if it is found
     * @return boolean on whether or not the user exists
     */
    public boolean doesUserExist(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * helper method to check and see if this user is
     * an Admin user
     *
     * @param username the string of the username to see if it is found
     * @return boolean on whether or not the user is an Admin user
     */
    public boolean checkAdmin(String username) throws UserNotFoundException {
        return this.getUser(username).getType().equals(User.UserType.ADMIN);
    }

    /**
     * helper method to check and see if this user is
     * an Organizer user
     *
     * @return boolean on whether or not the user is an Organizer user
     */
    public boolean checkOrganizer(User user) {
        return user.getType().equals(User.UserType.ORGANIZER);
    }

    /**
     * Gets the list of users
     *
     * @return the list of users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Returns a sorted user list in the order of attendees, organizers, speakers, vips, and admins
     * respectively
     *
     * @return A list of Users sorted by user type
     */
    public List<User> getUsersSorted() {

        List<User> usersSorted = new ArrayList<>();

        List<User> attendees = new ArrayList<>();
        List<User> organizers = new ArrayList<>();
        List<User> speakers = new ArrayList<>();
        List<User> vips = new ArrayList<>();
        List<User> admins = new ArrayList<>();

        for (User user : users) {
            switch (user.getType()) {
                case ATTENDEE: {
                    attendees.add(user);
                    break;
                }
                case ORGANIZER: {
                    organizers.add(user);
                    break;
                }
                case SPEAKER: {
                    speakers.add(user);
                    break;
                }
                case VIP: {
                    vips.add(user);
                    break;
                }
                case ADMIN: {
                    admins.add(user);
                }
            }
        }

        usersSorted.addAll(attendees);
        usersSorted.addAll(organizers);
        usersSorted.addAll(speakers);
        usersSorted.addAll(vips);
        usersSorted.addAll(admins);

        return usersSorted;
    }

    /**
     * the user login for when a user wants to log in to the system
     *
     * @param username the username to be submitted
     * @param password the password to be submitted
     * @throws IncorrectPasswordException if the password is wrong for the correct username
     * @throws UserNotFoundException      if the username can not be found in the system
     */
    public void userLogin(String username, String password) throws IncorrectPasswordException, UserNotFoundException {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                if (!user.verifyPassword(password)) {
                    throw new IncorrectPasswordException(username);
                }
                currentlyLoggedIn = user;
                return;
            }
        }
        throw new UserNotFoundException(username);
    }

    /**
     * returns an array list of UUIDs of the given array list of user inputs
     *
     * @param u an array list of user inputs
     * @return an array list of UUIDS for the corresponding users
     */
    public ArrayList<UUID> listOfID(ArrayList<User> u) {
        ArrayList<UUID> usersID = new ArrayList<>();
        for (User user : u) {
            usersID.add(user.getId());
        }
        return usersID;
    }


    /**
     * gets a list of all the attendees in the conference system
     *
     * @return List of attendees in the conference system
     */
    public List<User> getAttendees() {
        ArrayList<User> attendees = new ArrayList<>();
        for (User user : users) {
            if (user.getType() == User.UserType.ATTENDEE) {
                attendees.add(user);
            }
        }
        return attendees;
    }


    /**
     * Method that gets all of the speakers in the conference system
     *
     * @return A list of speakers in the conference system
     */
    public List<User> getSpeakers() {
        ArrayList<User> speakers = new ArrayList<>();
        for (User user : users) {
            if (user.getType() == User.UserType.SPEAKER) {
                speakers.add(user);
            }
        }
        return speakers;
    }

    /**
     * A helper method that generates the label texts for the delete account scene.
     *
     * @return A list of stings that will become label texts in the delete account scene
     */
    public List<String> getDeleteAccountLabels() {
        ArrayList<String> labels = new ArrayList<>();
        for (User user : users) {
            labels.add(user.getUsername() + ": " + user.getType());
        }
        return labels;
    }

    /**
     * A helper method that generates the name of the user from given user id.
     *
     * @param id the user id of this user
     * @return The name of the user who has the given user id
     */
    public String getName(UUID id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user.getUsername();
            }
        }
        return null;
    }

    /**
     * Determines a UserType from a String
     *
     * @param type - The type represented by a String
     * @return a UserType - the corresponding UserType
     * @throws UserTypeDoesNotExistException if the <type> is not descriptive of
     *                                       any UserType enum
     */
    public User.UserType parseType(String type) throws UserTypeDoesNotExistException {
        type = type.toUpperCase();

        switch (type) {
            case "ATTENDEE":
                return User.UserType.ATTENDEE;
            case "VIP":
                return User.UserType.VIP;
            case "ADMIN":
                return User.UserType.ADMIN;
            case "SPEAKER":
                return User.UserType.SPEAKER;
            case "ORGANIZER":
                return User.UserType.ORGANIZER;
            default:
                throw new UserTypeDoesNotExistException("User type: " + type + " is not a valid user type");
        }
    }

    /**
     * Checks if this instance of the manager contains a given user
     *
     * @param user - the user we wish to check
     * @return a boolean - whether or not the user is being kept track of
     * by this instance of UserManager
     */
    public boolean isUserInManager(User user) {
        return users.contains(user);
    }
}
