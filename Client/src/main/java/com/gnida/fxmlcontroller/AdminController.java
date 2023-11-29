package com.gnida.fxmlcontroller;

import com.gnida.SceneLoader;
import jakarta.annotation.PostConstruct;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class AdminController {
    public Button backButton;
    public Button settingsButton;
    public Button budgetsButton;
    public Button transactionsButton;
    public Button usersButton;

    private Scene currentScene;

    public void onBackClicked(ActionEvent actionEvent) {
        SceneLoader.getPreviousRoot(backButton.getScene());
    }

    public void onBudgetsClicked(ActionEvent actionEvent) {

    }

    public void onUsersClicked(ActionEvent actionEvent) {

    }

    public void onTransactionsClicked(ActionEvent actionEvent) {

    }

    public void onSettnngsClicked(ActionEvent actionEvent) {

    }
}
