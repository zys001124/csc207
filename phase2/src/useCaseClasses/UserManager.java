package useCaseClasses;


import entities.User;
import exceptions.*;
import observers.Observable;

import java.util.ArrayList;
import java.util.Arrays;
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
     * Creates a UserManager with the given list of users
     * @param users - The list of users this UserManager will manage
     */
    public UserManager(List<User> users) {
        this.users = users;
    }

    /**
     * finds a user in the list of users in the conference system and returns that user.
     * returns its UUID or null if the user is not found.
     *
     * @param username String of the username that will be used to find the user in the System
     * @return the User's UUID to the corresponding username or null if user does not exist
     */
    public UUID getUserID(String username){
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
    public User removeUser(UUID id, boolean fromDatabase){
        List<User> usersToRemove = new ArrayList<>();
        for(User user: users){
            int index = users.indexOf(user);
            if(user.getId().equals(id)){
                usersToRemove.add(users.remove(index));
                notifyObservers(usersToRemove, false, fromDatabase);
                return usersToRemove.get(0);
            }
        }
        return null;
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
    public void addUser(User.UserType type, String username, String password) throws UsernameAlreadyExistsException{
        if (doesUserExist(username)) {
            throw new UsernameAlreadyExistsException("Username: " + username + " is taken");
        }
        User user = new User(type, username, password, UUID.randomUUID());
        List<User> usersToAdd = new ArrayList<>();
        usersToAdd.add(user);
        users.addAll(usersToAdd);
        notifyObservers(usersToAdd, true, false);
    }


    public void addUserFromDatabase(User.UserData data) {
        User user = User.fromUserData(data);

        List<User> usersToAdd = new ArrayList<>();
        usersToAdd.add(user);
        users.addAll(usersToAdd);
        notifyObservers(usersToAdd, true, true);
    }

    public void changeUserFromDatabase(User.UserData data) {
        User user = User.fromUserData(data);

        List<User> usersToAdd = new ArrayList<>();
        usersToAdd.add(user);
        removeUser(user.getId(), true);
        users.addAll(usersToAdd);
        notifyObservers(usersToAdd, true, true);
    }

    /**
     * helper method to check and see if the username exists
     * called in addUser
     *
     * @param username the string of the username to see if it is found
     * @return boolean on wether or not the user exists
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
     * Gets the list of users
     * @return the list of users
     */
    public List<User> getUsers() {
        return users;
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
    
    public List<User> getAttendees() {
        ArrayList<User> attendees = new ArrayList<>();
        for(User user: users) {
            if(user.getType() == User.UserType.ATTENDEE) {
                attendees.add(user);
            }
        }
        return attendees;
    }

    public List<User> getOrganizers() {
        ArrayList<User> organizers = new ArrayList<>();
        for(User user: users) {
            if(user.getType() == User.UserType.ORGANIZER) {
                organizers.add(user);
            }
        }
        return organizers;
    }

    public List<User> getVips() {
        ArrayList<User> vips = new ArrayList<>();
        for(User user: users) {
            if(user.getType() == User.UserType.VIP) {
                vips.add(user);
            }
        }
        return vips;
    }

    public List<User> getAdmins() {
        ArrayList<User> admins = new ArrayList<>();
        for(User user: users) {
            if(user.getType() == User.UserType.ADMIN) {
                admins.add(user);
            }
        }
        return admins;
    }

    public List<User> getSpeakers() {
        ArrayList<User> speakers = new ArrayList<>();
        for(User user: users) {
            if(user.getType() == User.UserType.SPEAKER) {
                speakers.add(user);
            }
        }
        return speakers;
    }

    /**
     * Determines a UserType from a String
     *
     * @param type - The type represented by a String
     * @return a UserType - the corresponding UserType
     * @throws UserTypeDoesNotExistException if the <type> is not descriptive of
     * any UserType enum
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

}
