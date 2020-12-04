package controllers;

import entities.Event;
import entities.Message;
import entities.User;
import exceptions.IncorrectObjectTypeException;
import handlers.SceneNavigator;
import observers.Observable;
import observers.Observer;
import useCaseClasses.EventManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;


public class EventCancelController extends Controller{

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="eventListView"
    private ListView<Label> eventListView; // Value injected by FXMLLoader

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="cancelButton"
    private Button cancelButton; // Value injected by FXMLLoader

    @FXML // fx:id="createMessageLabel"
    private Label createMessageLabel; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert eventListView != null : "fx:id=\"eventListView\" was not injected: check your FXML file 'Cancel Event.fxml'.";
        assert backButton != null : "fx:id=\"backButton\" was not injected: check your FXML file 'Cancel Event.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'Cancel Event.fxml'.";
        assert createMessageLabel != null : "fx:id=\"createMessageLabel\" was not injected: check your FXML file 'Cancel Event.fxml'.";

    }

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

        String eventname = eventListView.getSelectionModel().getSelectedItem().getText().split(" on")[0];

        InputProcessResult result = cancelEvent(eventname);

        if (result == InputProcessResult.SUCCESS){
            label = "Event canceled successfully.";
        } else if (result == InputProcessResult.EVENT_DOES_NOT_EXIST) {
            label = "This event does not exist. Try again.";
        } else if (result == InputProcessResult.USER_DID_NOT_ORGANIZE_EVENT) {
            label = "This event is not organized by you. Try again.";
        }
        createMessageLabel.setText(label);
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
        User.UserType currentUserType = userManager.getCurrentlyLoggedIn().getType();
        return currentUserType == User.UserType.ADMIN; //True if Admin, False if Organizer
    }

    @Override
    public void setEventManager(EventManager eventManager) {
        super.setEventManager(eventManager);

        setEventList();
        eventManager.addObserver(new Observer() {
            @Override
            public void update(Observable o, List<?> changes, boolean addedOrChanged, boolean retrievedFromDatabase) throws IncorrectObjectTypeException {
                setEventList();
            }
        });
    }

    private List<Label> getEventLabels(Collection<Event> events) {
        ArrayList<Label> labels = new ArrayList<>();
        for(Event event: events) {
            String labelText = event.getEventTitle() +" on "+event.getEventTime().format(DateTimeFormatter.ofPattern("MMMM dd, HH:mm"))+".";
            if(event.getSpeakerId().size() > 0) {
                labelText = labelText + " Hosted by";
                for(int i = 0; i<event.getSpeakerId().size(); i++) {
                    UUID speakerId = event.getSpeakerId().get(i);
                    labelText = labelText + " " + userManager.getUser(speakerId).getUsername();
                    if(i != event.getSpeakerId().size()-1) {
                        labelText = labelText + ",";
                    }
                }
            }
            Label eventLabel = new Label(labelText);
            labels.add(eventLabel);
        }
        return labels;
    }

    private void setEventList(){
        eventListView.getItems().setAll(getEventLabels(eventManager.getEvents()));
    }


}

