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

    public void onBudgetsClicked(ActionEvent actionEvent) {

    }

    public void onUsersClicked(ActionEvent actionEvent) {

    }

    public void onTransactionsClicked(ActionEvent actionEvent) {

    }

    public void onSettnngsClicked(ActionEvent actionEvent) {

    }

    @Override
    protected void initialize() {
        super.initialize();

        transactionsButton.setOnAction(actionEvent -> SceneManager.loadScene(super.scene, Screen.TRANSACTIONS_ALL));
        budgetsButton.setOnAction(actionEvent -> SceneManager.loadScene(super.scene, Screen.BUDGETS_ALL));
        usersButton.setOnAction(actionEvent -> SceneManager.loadScene(super.scene, Screen.USERS_ALL));
        settingsButton.setOnAction(actionEvent -> SceneManager.loadScene(super.scene, Screen.SETTINGS));
    }


}
