package UI;

import controllers.MessageInputController;
import presenters.MessageInputPresenter;

import java.util.Scanner;

public class MessageInputView extends ConsoleView {
    private MessageInputController messageInputController;
    private MessageInputPresenter messageInputPresenter;

    public MessageInputView(MessageInputController messageInputController,
                            MessageInputPresenter messageInputPresenter){
        this.messageInputController = messageInputController;
        this.messageInputPresenter = messageInputPresenter;
    }

    @Override
    public ConsoleViewType runFlow(Scanner inputScanner) {
        return null;
    }
}
