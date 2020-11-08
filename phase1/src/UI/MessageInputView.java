package UI;

import controllers.MessageInputController;
import presenters.MessageInputPresenter;

public class MessageInputView {
    private MessageInputController messageInputController;
    private MessageInputPresenter messageInputPresenter;

    public MessageInputView(MessageInputController messageInputController,
                            MessageInputPresenter messageInputPresenter){
        this.messageInputController = messageInputController;
        this.messageInputPresenter = messageInputPresenter;
    }
}
