package presenters;

import controllers.InputProcessResult;

public abstract class Presenter {

    public abstract String getPreInputText();

    public abstract String getInputResponseText(InputProcessResult result);

}
