package controllers;

import javafx.fxml.FXML;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

/**
 * Abstract class that holds the UserManager, MessageManager, EventManager, and SceneNavigator
 * for this conference system
 */
public abstract class Controller {

    protected UserManager userManager;
    protected MessageManager messageManager;
    protected EventManager eventManager;

    private SceneNavigator sceneNavigator;

    /**
     * This method is called by the FXMLLoader when initialization is complete
     */
    @FXML
    abstract void initialize();

    /**
     * Sets the user manager to the corresponding userManager
     *
     * @param userManager the userManager to be set to
     */
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * Sets the message manager to the corresponding messageManager
     *
     * @param messageManager the messageManager to be set to
     */
    public void setMessageManager(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    /**
     * Sets the event manager to the corresponding eventManager
     *
     * @param eventManager the eventManager to be set to
     */
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    /**
     * sets the sceneNavigator to the corresponding sceneNavigator
     *
     * @param sceneNavigator the sceneNavigator to be set to
     */
    public void setSceneNavigator(SceneNavigator sceneNavigator) {
        this.sceneNavigator = sceneNavigator;
    }

    /**
     * Switches the scene to the corresponding scene type
     *
     * @param sceneViewType The scene type for the new Scene to be set to
     */
    protected void setSceneView(SceneViewType sceneViewType) {
        sceneNavigator.switchSceneView(sceneViewType);
        // cases based on enum to switch scenes with scene navigator
    }

}
