package controllers;

import entities.Message;
import entities.User;
import exceptions.IncorrectObjectTypeException;
import exceptions.InvalidUserTypeException;
import exceptions.NoMessageException;
import exceptions.UserNotFoundException;
import handlers.SceneNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import observers.Observable;
import observers.Observer;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MessageUserSceneController extends Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="userListView"
    private ListView<Label> userListView; // Value injected by FXMLLoader

    @FXML
    private Button backButton;

    @FXML
    private ListView<Label> messageListView;

    @FXML
    private TextField messageField;

    @FXML
    private Button sendMessageButton;

    @FXML // fx:id="cantSendToUserMessage"
    private Label cantSendToUserMessage; // Value injected by FXMLLoader

    private DateTimeFormatter dateTimeFormatter;

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert userListView != null : "fx:id=\"userListView\" was not injected: check your FXML file 'Message User.fxml'.";
        dateTimeFormatter = DateTimeFormatter.RFC_1123_DATE_TIME;
        assert cantSendToUserMessage != null : "fx:id=\"cantSendToUserMessage\" was not injected: check your FXML file 'Message User.fxml'.";

        userListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> setMessageList());
    }

    @Override
    public void setUserManager(UserManager userManager) {
        super.setUserManager(userManager);

        setUserList();
        userManager.addObserver((o, changes, addedOrChanged, retrievedFromDatabase) -> setUserList());
    }

    @Override
    public void setMessageManager(MessageManager messageManager) {
        super.setMessageManager(messageManager);

        messageManager.addObserver(new Observer<Message>() {
            @Override
            public void update(Observable o, List<Message> changes, boolean addedOrChanged, boolean retrievedFromDataBase) throws IncorrectObjectTypeException {
                //Using set so no duplicates are made
                Set<Message> newMessages = new TreeSet<>();
                UUID currentUserID = userManager.getCurrentlyLoggedIn().getId();

                for (Object change : changes) {
                    Message message = (Message) change;
                    UUID senderID = message.getSenderId();
                    UUID recipientID = message.getRecipientId();

                    // if the current user is not the sender or recipient we do not care about this message
                    if (!senderID.equals(currentUserID) && !recipientID.equals(currentUserID)) {
                        continue;
                    }

                    newMessages.add(message);
                }
                messageListView.getItems().addAll(getMessageLabels(newMessages));

            }
        });
    }

    private List<Label> getUserLabels(Collection<User> users) {
        ArrayList<Label> labels = new ArrayList<>();
        for (User user : users) {
            labels.add(new Label(user.getUsername() + ": " + user.getType()));
        }
        return labels;
    }

    private List<Label> getMessageLabels(Collection<Message> messages) {
        List<Label> labels = new ArrayList<>();
        for (Message message : messages) {
            labels.add(getMessageLabel(message));
        }
        return labels;
    }

    private Label getMessageLabel(Message message) {
        String senderUsername = userManager.getUser(message.getSenderId()).getUsername();
        String timeSent = message.getTimeSent().toString();
        return new Label(timeSent + " from " + senderUsername + ": " + message);
    }

    @FXML
    void onSendMessageButtonClicked(ActionEvent event) {
        String label = "";

        String text = messageField.getText();

        if (text.equals("")) {
            return;
        }

        messageField.clear();

        Label userLabel = userListView.getSelectionModel().getSelectedItems().get(0);
        String recipientUsername = userLabel.getText().split(":")[0];

        UUID recipient = userManager.getUserID(recipientUsername);
        UUID sender = userManager.getCurrentlyLoggedIn().getId();

        try {
            messageManager.sendIndividualMessage(userManager.getCurrentlyLoggedIn().getType(), sender, userManager.getUser(recipient).getType(), recipient, text);
        } catch (InvalidUserTypeException e) {
            label = "You can't message this user.";
        } catch (NoMessageException e) {
            label = "You can't send a message to this attendee right now.";
        }
        cantSendToUserMessage.setText(label);
    }

    @FXML
    void onBackButtonClicked(ActionEvent event) {
        User.UserType currentUserType = userManager.getCurrentlyLoggedIn().getType();
        if (currentUserType == User.UserType.SPEAKER) {
            setSceneView(SceneNavigator.SceneViewType.SPEAKER_MAIN_MENU);
        } else if (currentUserType == User.UserType.ORGANIZER) {
            setSceneView(SceneNavigator.SceneViewType.ORGANIZER_MAIN_MENU);
        } else if (currentUserType == User.UserType.ADMIN) {
            setSceneView(SceneNavigator.SceneViewType.ADMIN_MAIN_MENU);
        } else if (currentUserType == User.UserType.ATTENDEE) {
            setSceneView(SceneNavigator.SceneViewType.ATTENDEE_MAIN_MENU);
        } else if (currentUserType == User.UserType.VIP) {
            setSceneView(SceneNavigator.SceneViewType.VIP_MAIN_MENU);
        }
    }

    private void setMessageList() {
        Label userLabel = userListView.getSelectionModel().getSelectedItems().get(0);
        String otherUsername = userLabel.getText().split(":")[0];
        try {
            List<Message> messages = messageManager.messagesBetweenTwo(userManager.getCurrentlyLoggedIn(),
                    userManager.getUser(otherUsername));
            messageListView.getItems().clear();
            messageListView.getItems().setAll(getMessageLabels(messages));
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void setUserList() {
        userListView.getItems().setAll(getUserLabels(userManager.getUsersSorted()));
    }
}
