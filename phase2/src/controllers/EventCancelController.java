package controllers;

import entities.Event;
import entities.User;
import exceptions.UserNotFoundException;
import handlers.SceneNavigator;
import presenters.EventCancelPresenter;
import useCaseClasses.EventManager;
import useCaseClasses.UserManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.SortedMap;
import java.util.UUID;

public class EventCancelController extends Controller{

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="eventListField"
    private TextArea eventListField; // Value injected by FXMLLoader

    @FXML // fx:id="eventNameField"
    private TextField eventNameField; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="cancelButton"
    private Button cancelButton; // Value injected by FXMLLoader

    @FXML // fx:id="createMessageLabel"
    private Label createMessageLabel; // Value injected by FXMLLoader

    @FXML
    void onBackButtonClicked(ActionEvent event) {
        if(checkUserType()){
            setSceneView(SceneNavigator.SceneViewType.ADMIN_MAIN_MENU);
        }
        if(!checkUserType()){
            setSceneView(SceneNavigator.SceneViewType.ORGANIZER_MAIN_MENU);
        }
    }

    @FXML
    void onCancelButtonClicked(ActionEvent event) {

        String label = "";

        String eventname = eventNameField.getText();

        InputProcessResult result = cancelEvent(eventname);

        if (result == InputProcessResult.SUCCESS){
            label = "Event canceled successfully";
            if(checkUserType()){
                setSceneView(SceneNavigator.SceneViewType.ADMIN_MAIN_MENU);
            }
            if(!checkUserType()){
                setSceneView(SceneNavigator.SceneViewType.ORGANIZER_MAIN_MENU);
            }
        } else if (result == InputProcessResult.EVENT_DOES_NOT_EXIST) {
            label = "This event does not exist. Try again.";
        } else if (result == InputProcessResult.USER_DID_NOT_ORGANIZE_EVENT) {
            label = "This event is not organized by you. Try again.";
        }
        createMessageLabel.setText(label);
    }


    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert eventNameField != null : "fx:id=\"eventNameField\" was not injected: check your FXML file 'Cancel Event.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'Cancel Event.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'Cancel Event.fxml'.";
        assert createMessageLabel != null : "fx:id=\"createMessageLabel\" was not injected: check your FXML file 'Cancel Event.fxml'.";
        assert eventListField != null : "fx:id=\"eventListField\" was not injected: check your FXML file 'Cancel Event.fxml'.";
    }

    private InputProcessResult cancelEvent(String eventName){
        if (!eventManager.eventTitleExists(eventName)) {
            return InputProcessResult.EVENT_DOES_NOT_EXIST;
        }

        User currentUser = userManager.getCurrentlyLoggedIn();
        Event currentEvent = eventManager.getEvent(eventName);

        if (!eventManager.hasOrganizedEvent(currentUser, currentEvent) &&
                !checkUserType()) {
            return InputProcessResult.USER_DID_NOT_ORGANIZE_EVENT;
        }

        eventManager.removeEvent(currentEvent.getId());
        return InputProcessResult.SUCCESS;
    }

    private boolean checkUserType(){
        try{
            if(userManager.checkAdmin(userManager.getCurrentlyLoggedIn().getUsername())){
                return true; //Admin
            }
        } catch(UserNotFoundException e){
        }
        return false; //Organizer
    }


}


///**
// * A controller for handling inputs when an organizer try
// * to cancel an existing event
// */
//public class EventCancelController {
//
//    private final EventManager Emanager;
//    private final UserManager Umanager;
//
//    /**
//     * Creates an EventCancelController with the given EventManager, EventCancelPresenter and UserManager
//     *
//     * @param Emanager  the EventManager this controller will use
//     * @param Umanager  the UserManager this controller will use
//     */
//
//    public EventCancelController(EventManager Emanager, UserManager Umanager) {
//        this.Emanager = Emanager;
//        this.Umanager = Umanager;
//    }
//
//    /**
//     * Handle the input given by the user and decides which screen menu to show given the input
//     *
//     * @param input - the input from user
//     * @return an InputProcessResult enum that details what happened as a result of the given input
//     */
//
//    public InputProcessResult getNextScreen(String input) {
//
//        if (input.equals("back")) {
//            return InputProcessResult.BACK;
//        }
//
//        if (!Emanager.eventTitleExists(input)) {
//            return InputProcessResult.EVENT_DOES_NOT_EXIST;
//        }
//
//        Event currentEvent = Emanager.getEvent(input);
//        User currentUser = Umanager.getCurrentlyLoggedIn();
//
//        if (!Emanager.hasOrganizedEvent(currentUser, currentEvent) &&
//                !Umanager.getCurrentlyLoggedIn().getType().equals(User.UserType.ADMIN)) {
//            return InputProcessResult.USER_DID_NOT_ORGANIZE_EVENT;
//        }
//
//        Emanager.removeEvent(currentEvent.getId());
//
//        return InputProcessResult.SUCCESS;
//    }
//
//}
