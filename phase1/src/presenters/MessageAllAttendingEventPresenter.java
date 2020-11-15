package presenters;

import controllers.InputProcessResult;
import useCaseClasses.EventManager;
import useCaseClasses.UserManager;

import java.util.List;

public class MessageAllAttendingEventPresenter extends Presenter {

    private final EventManager eventManager;
    private final UserManager userManager;

    /**
     * Sets the right EventManager and UserManager Use case classes for the current user logged in
     *
     * @param userManager  the userManager for this instance
     * @param eventManager the eventManager for this instance
     */
    public MessageAllAttendingEventPresenter(UserManager userManager, EventManager eventManager) {
        this.userManager = userManager;
        this.eventManager = eventManager;
    }

    /**
     * gets the pre text that will be displayed to the screen for the user when they choose this option
     * Displays a list the user can see of which event the speaker is hosting
     *
     * @return string for the user to see on the screen
     */
    public String getPreInputText() {
        return "Welcome. Here are the event names of the events that you are hosting and you can message: \n" + getEvents(eventManager.listOfEventsHosting(userManager.getCurrentlyLoggedIn())) +
                "Please enter the event name of the event you would like to message all of the attendees for:\n" +
                "Enter \"back\" to go back to the main menu.";
    }

    /**
     * the message the user will see after they put in the event name that is not 'back'
     *
     * @return String that will display to the screen asking for the message to send out
     */
    public String getMessage() {
        return "Please enter the message you wish to send out to your users:";
    }

    /**
     * Helper method for getPreInput text that gets all of the events the speaker is hosting and puts them in a list
     *
     * @param events the events the speaker is hosting
     * @return a string where each event the speaker is hosting is on a new line.
     */
    private String getEvents(List<String> events) {
        StringBuilder s = new StringBuilder();
        for (String e : events) {
            s.append(e).append("\n");
        }
        return s.toString();
    }
    //public void setInputResponse(String s){ inputResponse = s;}

    /**
     * Returns a message that will be displayed to the screen after the user puts in their input.
     * Either could understand the input or can't understand the input
     *
     * @param result the result that is gotten from the controller to see what string to display
     * @return A string telling the user if the input they put in is valid and which screen they will
     * go to next
     */
    public String getInputResponseText(InputProcessResult result) {
        if (result == InputProcessResult.INVALID_INPUT) {
            return "Could Not understand your input. Staying on this screen";
        } else {
            return "Message successfully sent. \nReturning to Main Menu";
        }
    }
}
