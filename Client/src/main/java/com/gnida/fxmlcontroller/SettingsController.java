package com.gnida.fxmlcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SettingsController extends GenericController {

    @FXML
    public Button switchThemeButton;

    boolean isDarkTheme = false;

    @Override
    protected void initialize() {
        backButton.setOnAction(actionEvent -> onBackButtonClick());

        switchThemeButton.setOnAction(actionEvent -> {
            isDarkTheme = !isDarkTheme;
            updateTheme();
        });

    }

    void updateTheme() {
        if (isDarkTheme) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add("/DarkTheme.css");
        } else {
            scene.getStylesheets().clear();
            scene.getStylesheets().add("/WhiteTheme.css");
        }

    }
}
