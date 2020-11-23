package useCaseClasses;


import entities.User;
import exceptions.IncorrectPasswordException;
import exceptions.UserNotFoundException;
import exceptions.UsernameAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A manager for managing all the users in the tech conference
 */
public class UserManager {

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
    public void addUser(User.UserType type, String username, String password) throws UsernameAlreadyExistsException {
        if (doesUserExist(username)) {
            throw new UsernameAlreadyExistsException("Username: " + username + " is taken");
        }
        User user = new User(type, username, password, UUID.randomUUID());
        users.add(user);
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


}
