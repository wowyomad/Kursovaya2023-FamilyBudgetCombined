package com.gnida.fxmlcontroller.admin;

import com.gnida.SceneManager;
import com.gnida.fxmlcontroller.GenericController;
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
        transactionsButton.setOnAction(actionEvent -> SceneManager.loadScene(super.scene, "/admin-transactions-view.fxml"));
        budgetsButton.setOnAction(actionEvent -> SceneManager.loadScene(super.scene, "/admin-budgets-view.fxml"));
        usersButton.setOnAction(actionEvent -> SceneManager.loadScene(super.scene, "/home-admin-view.fxml"));
        settingsButton.setOnAction(actionEvent -> SceneManager.loadScene(super.scene, "/settings-view.fxml"));
    }


}
