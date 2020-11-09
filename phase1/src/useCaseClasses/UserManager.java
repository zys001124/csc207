package useCaseClasses;


import entities.User;
import entities.Message;
import exceptions.IncorrectPasswordException;
import exceptions.UserNotFoundException;

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
        User search = null;
        for(User u: users) {
            if(u.getId().equals(id)) {
                search = u;
                break;
            }
        }
        return search;
    }

    public User getCurrentlyLoggedIn() {return currentlyLoggedIn;}

    public void addUser(User user){
        users.add(user);
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
                return;
            }
        }
        throw new UserNotFoundException(username);
    }


}
