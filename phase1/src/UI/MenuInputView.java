package UI;

import controllers.MenuInputController;
import presenters.MenuInputPresenter;

public class MenuInputView {
    private MenuInputController menuInputController;
    private MenuInputPresenter menuInputPresenter;

    public MenuInputView(MenuInputController menuInputController, MenuInputPresenter menuInputPresenter){
        this.menuInputController = menuInputController;
        this.menuInputPresenter = menuInputPresenter;
    }
}
