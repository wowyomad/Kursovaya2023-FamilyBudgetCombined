package com.gnida.fxmlcontroller;

import com.gnida.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

public class AdminController extends GenericController {
    public Button backButton;
    public Button settingsButton;
    public Button budgetsButton;
    public Button transactionsButton;
    public Button usersButton;

    private Scene currentScene;

    public void onBackClicked(ActionEvent actionEvent) {
        SceneManager.getPreviousRoot(backButton.getScene());
    }

    public void onBudgetsClicked(ActionEvent actionEvent) {
        SceneManager.loadScene(scene, "/budgets-view.fxml");
    }

    public void onUsersClicked(ActionEvent actionEvent) {

    }

    public void onTransactionsClicked(ActionEvent actionEvent) {

    }

    public void onSettnngsClicked(ActionEvent actionEvent) {

    }


    @FXML void initialize() {


    }



}
