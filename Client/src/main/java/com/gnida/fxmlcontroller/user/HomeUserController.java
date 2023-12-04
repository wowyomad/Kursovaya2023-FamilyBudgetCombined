package com.gnida.fxmlcontroller.user;

import com.gnida.SceneManager;
import com.gnida.fxmlcontroller.GenericController;
import com.gnida.fxmlcontroller.windows.Screen;
import javafx.scene.control.Button;

public class HomeUserController extends GenericController {

    public Button settingsButton;
    public Button budgetsButton;

    @Override
    protected void initialize()  {
        super.initialize();
        scene.getWindow().setWidth(900);
        scene.getWindow().setHeight(600);
        settingsButton.setOnAction(actionEvent -> SceneManager.loadScene(Screen.SETTINGS));
        budgetsButton.setOnAction(actionEvent -> SceneManager.loadScene(Screen.BUDGETS_USER));

    }
}
