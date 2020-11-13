package useCaseClasses;


import entities.User;
import entities.Message;
import exceptions.IncorrectPasswordException;
import exceptions.UserNotFoundException;
import exceptions.UsernameAlreadyExistsException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//TODO remove messages reference, polish up. Messages should be handled by MessageManager only
public class UserManager {

    private List<User> users;

    private User currentlyLoggedIn;

    public UserManager(){
        users = new ArrayList<>();
    }

    public UserManager(List<User> users){
        this.users = users;
    }

    public User getUser(UUID id) {
        //TODO this method should be able to throw a UserNotFoundException but isn't supplied
        // a username, so this is impossible. It's not used at all so I'm not sure if we need it.
        for(User u: users) {
            if(u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    public User getUser(String username) throws UserNotFoundException{
        for(User u: users) {
            if(u.getUsername().equals(username))
            {
                return u;
            }
        }
        throw new UserNotFoundException(username);
    }

    public User getCurrentlyLoggedIn() {return currentlyLoggedIn;}

    public void addUser(User user) throws UsernameAlreadyExistsException {
        if(doesUserExist(user.getUsername())) {
            throw new UsernameAlreadyExistsException("Username: "+user.getUsername()+" is taken");
        }
        users.add(user);
    }

    public void addUser(User.UserType type, String username, String password) throws UsernameAlreadyExistsException {
        if(doesUserExist(username)) {
            throw new UsernameAlreadyExistsException("Username: "+username+" is taken");
        }
        User user = new User(type, username, password, UUID.randomUUID());
        users.add(user);
    }

    public boolean doesUserExist(String username) {
        for(User user: users) {
            if(user.getUsername().equals(username))
            {
                return true;
            }
        }
        return false;
    }

    public User removeUser(UUID id){
        for(int i = 0; i<users.size(); i++) {
            if(users.get(i).getId().equals(id)) {
                return users.remove(i);
            }
        }
        // TODO maybe throw exception if the user doesnt exist.
        return null;
    }

    public List<User> getusers(){
        return users;
    }

    public void userLogin(String username, String password) throws IncorrectPasswordException, UserNotFoundException {
        for(User user: users) {
            if(user.getUsername().equals(username)) {
                if(!user.verifyPassword(password)) {
                    throw new IncorrectPasswordException(username);
                }
                currentlyLoggedIn = user;
                return;
            }
        }
        throw new UserNotFoundException(username);
    }


}
