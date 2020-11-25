package presenters;

import controllers.InputProcessResult;

/**
 * A presenter meant to be used for generating Strings to be printed
 * to the console when an Organizer is making an event
 */
public class EventCreationPresenter extends Presenter {

    private final String inputResponse = "";

    /**
     * Gets the String that should be printed before a user makes an input
     *
     * @return the String that should be printed before an input is made
     */
    public String getPreInputText() {
        return "There are 6 possible rooms for speeches." +
                "The room number is from 0-5. Every speech is one hour long and it must be schedule at exact hour\n" +
                "if there is multiple speakers separate each speaker with ':' for no speaker leave it empty\n"+
                "To add an event, enter information in following way: " +
                "title,startTime,endTime,speakerUsernames,roomNumber,capacity,Vip only \n" +
                "Vip only should only be written as 'true' or 'false'\n" +
                "Time should be written in this form yyyy-MM-dd HH:mm\n" +
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
    public String getInputResponseText(InputProcessResult result) {
        if (result == InputProcessResult.BACK) {
            return "";
        } else if (result == InputProcessResult.USER_NOT_FOUND) {
            return "The speaker user is not a register user";
        } else if (result == InputProcessResult.USER_NOT_SPEAKER) {
            return "The given speaker is not a speaker";
        } else if (result == InputProcessResult.TIMESLOT_FULL) {
            return "This time slot is full";
        } else if (result == InputProcessResult.SUCCESS) {
            return "The event is added";
        } else if (result == InputProcessResult.SPEAKER_OCCUPIED) {
            return "The speaker is occupied in at this time";
        } else {
            return "The room is already booked. Try another room";
        }

    }

}
