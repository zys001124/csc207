package presenters;

import controllers.InputProcessResult;

/**
 * A presenter for a ConsoleView
 */
public abstract class Presenter {

    /**
     * Gets the String that should be printed to the console
     * before a user is given the opportunity to make an input
     *
     * @return a String - the text to be printed before a user
     * makes an input
     */
    public abstract String getPreInputText();

    /**
     * Gets the String that should be displayed on screen as a result
     * of the users input
     *
     * @param result - the InputProcessResult that determines what the
     *               response to the users input should be
     * @return a String - the programs response for the users input
     */
    public abstract String getInputResponseText(InputProcessResult result);

}
