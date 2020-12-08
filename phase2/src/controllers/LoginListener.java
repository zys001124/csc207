package controllers;

/**
 * An interface for classes that wish to perform actions
 * on a successful login
 */
public interface LoginListener {

    /**
     * Method called when a login has successfully
     * been completed
     */
    void onSuccessfulLogin();
}
