package com.gnida.fxmlcontroller;

import com.gnida.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UserController extends GenericController {

    @FXML
    private Button budgetsButton;

    @FXML
    private Button profileButton;

    @FXML
    private Button settingsButton;

    @Override
    protected void initialize() {
        backButton.setOnAction(actionEvent -> onBackButtonClick());

        budgetsButton.setOnAction(actionEvent -> SceneManager.loadScene(scene, "/user-budgets-view.fxml"));

        profileButton.setOnAction(actionEvent -> SceneManager.loadScene(scene, "/user-profile-view.fxml"));

        settingsButton.setOnAction(actionEvent -> SceneManager.loadScene(scene, "/user-setttings-view.fxml"));

    }

}
