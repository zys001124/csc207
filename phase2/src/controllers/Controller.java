package controllers;

import handlers.SceneNavigator;
import handlers.SceneNavigator.SceneView;
import javafx.fxml.FXML;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

public abstract class Controller {

    protected UserManager userManager;
    protected MessageManager messageManager;
    protected EventManager eventManager;

    private SceneNavigator sceneNavigator;

    @FXML
    abstract void initialize();

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public void setMessageManager(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void setSceneNavigator(SceneNavigator sceneNavigator) {
        this.sceneNavigator = sceneNavigator;
    }

    protected void setSceneView(SceneView sceneView) {
        sceneNavigator.switchSceneView(sceneView);
        // cases based on enum to switch scenes with scene navigator
    }
}
