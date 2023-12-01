package com.gnida.fxmlcontroller;

import com.gnida.SceneManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class AdminController extends GenericController {

    public Button settingsButton;
    public Button budgetsButton;
    public Button transactionsButton;
    public Button usersButton;

    public void onBackClicked(ActionEvent actionEvent) {
        SceneManager.getPreviousRoot(backButton.getScene());
    }

    public void onBudgetsClicked(ActionEvent actionEvent) {
        SceneManager.loadScene(scene, "/admin-budgets-view.fxml");
    }

    public void onUsersClicked(ActionEvent actionEvent) {}

    public void onTransactionsClicked(ActionEvent actionEvent) {

    }

    public void onSettnngsClicked(ActionEvent actionEvent) {

    }

    @Override
    protected void initialize() {
        backButton.setOnAction(actionEvent -> super.onBackButtonClick());
        transactionsButton.setOnAction(actionEvent -> SceneManager.loadScene(super.scene, "/admin-transactions-view.fxml"));
        budgetsButton.setOnAction(actionEvent -> SceneManager.loadScene(super.scene, "/admin-budgets-view.fxml"));
        usersButton.setOnAction(actionEvent -> SceneManager.loadScene(super.scene, "/admin-view.fxml"));
        settingsButton.setOnAction(actionEvent -> SceneManager.loadScene(super.scene, "/setting-view.fxml"));
    }


}
