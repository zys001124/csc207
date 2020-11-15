package presenters;

import controllers.InputProcessResult;

/**
 * A presenter meant to be used for generating Strings to be printed
 * to the console when an Organizer is making a Speaker account
 */
public class CreateSpeakerAccountPresenter extends Presenter {

    /**
     * Gets the String that should be printed before a user makes an input
     *
     * @return the String that should be printed before an input is made
     */
    @Override
    public String getPreInputText() {
        return "To create a speaker account type in the following: [username] [password] \n" +
                "Type \"back\" to return to the menu";
    }

    /**
     * Gets the String that should be printed after the user has
     * made an input
     *
     * @param result - the InputProcessResult that determines what the
     *               response to the users input should be
     * @return the String that should be printed after the user has
     * made an input
     */
    @Override
    public String getInputResponseText(InputProcessResult result) {
        if (result == InputProcessResult.INVALID_INPUT) {
            return "Expecting two, and only two inputs. Try again.";
        } else if (result == InputProcessResult.SUCCESS) {
            return "Speaker added successfully";
        } else if (result == InputProcessResult.USERNAME_TAKEN) {
            return "Username taken. Try again.";
        } else {
            return "";
        }
    }
}
