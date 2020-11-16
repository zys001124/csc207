package presenters;

import controllers.InputProcessResult;

/**
 * The presenter meant to be used by the MessageAllSpeakersView, for when messaging all speakers at the conference
 */
public class MessageAllSpeakersPresenter extends Presenter {

    /**
     * Generates the String that is to be displayed right before user makes any inputs
     *
     * @return A string that is to be displayed right before any inputs are made
     */
    @Override
    public String getPreInputText() {
        return "Please enter the message you wish to be sent to all speakers (type q to quit):";
    }

    /**
     * Gets the text that should be printed after the user has made an input. Indicates whether the messages
     * are sent or the UI is returning to the main menu
     *
     * @param result - the InputProcessResult that determines what the
     *               response to the users input should be
     * @return a String - The text that should be printed as a result of <result>
     */
    @Override
    public String getInputResponseText(InputProcessResult result) {
        switch (result) {
            case SUCCESS:
                return "Messages sent successfully!";
            case NAVIGATE_TO_MAIN_MENU:
                return "Returning to main menu.";
        }
        return "";
    }
}
