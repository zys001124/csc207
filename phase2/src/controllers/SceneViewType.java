package controllers;

/**
 * Each value of the enum represents a View (a scene in javafx). Removes the need to pass around scene references.
 */
public enum SceneViewType {

    LOGIN, // Finished
    SPEAKER_MAIN_MENU, // Finished
    ORGANIZER_MAIN_MENU, //Finished
    ATTENDEE_MAIN_MENU, //Finished
    ADMIN_MAIN_MENU, //Finished
    VIP_MAIN_MENU, //Finished
    MESSAGE_USER, //Finished
    MESSAGE_ALL_ATTENDING_EVENT, //Finished
    MESSAGE_ALL_SPEAKERS, //Finished
    MESSAGE_ALL_ATTENDEES, //Finished
    EVENT_SCHEDULE, //Finished
    CREATE_EVENT, //Finished
    CANCEL_EVENT, //Finished
    ENROLL_IN_EVENT, // Finished
    UNENROLL_IN_EVENT, // Finished
    CREATE_ACCOUNT, //Finished
    DELETE_ACCOUNT, //Finished
    CHANGE_CAPACITY; //Finished

    private String fxmlPath = "";

    /**
     * Gets the filepath of the fxml
     *
     * @return A String - the filepath (with working directory as the root)
     * of the fxml file for this sceneview
     */
    public String getFxmlPath() {
        return fxmlPath;
    }

    /**
     * Sets the path (with working directory as root) of th fxml file for the corresponding scene type
     *
     * @param path - the filepath of the fxml
     */
    public void setFxmlPath(String path) {
        fxmlPath = path;
    }
}
