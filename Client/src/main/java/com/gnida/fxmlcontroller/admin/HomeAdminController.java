package com.gnida.fxmlcontroller.admin;

import com.gnida.SceneManager;
import com.gnida.fxmlcontroller.GenericController;
import com.gnida.fxmlcontroller.windows.Screen;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class HomeAdminController extends GenericController {

    public Button settingsButton;
    public Button budgetsButton;
    public Button transactionsButton;
    public Button usersButton;
    public Button reportsButton;

    @Override
    protected void initialize() {
        super.initialize();

        transactionsButton.setOnAction(actionEvent -> SceneManager.loadScene(Screen.TRANSACTIONS_ALL));
        budgetsButton.setOnAction(actionEvent -> SceneManager.loadScene(Screen.BUDGETS_ALL));
        usersButton.setOnAction(actionEvent -> SceneManager.loadScene(Screen.USERS_ALL));
        settingsButton.setOnAction(actionEvent -> SceneManager.loadScene(Screen.SETTINGS));
    }


}
