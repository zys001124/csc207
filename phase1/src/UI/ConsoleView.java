package UI;

import entities.Event;
import entities.User;
import useCaseClasses.EventManager;
import useCaseClasses.MessageManager;
import useCaseClasses.UserManager;

import java.util.Scanner;

//TODO place base functions
public abstract class ConsoleView {

//    protected MessageManager messageManager;
//    protected UserManager userManager;
//    protected EventManager eventManager;
//
//    public ConsoleView(MessageManager messageManager, UserManager userManager, EventManager eventManager) {
//        this.messageManager = messageManager;
//        this.userManager = userManager;
//        this.eventManager = eventManager;
//    }

    public abstract ConsoleViewType runFlow(Scanner inputScanner);

    public enum ConsoleViewType {
        LOGIN,
        MAIN_MENU
    }
}
