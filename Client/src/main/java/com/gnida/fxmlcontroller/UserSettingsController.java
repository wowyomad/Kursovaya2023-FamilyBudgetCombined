package com.gnida.fxmlcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UserSettingsController extends GenericController {

    @FXML
    public Button themSwitchButton;
    @FXML
    Button backButton;


    boolean isDarkTheme = false;
    @FXML
    void initialize() {
        backButton.setOnAction(actionEvent -> onBackButtonClick());

        themSwitchButton.setOnAction(actionEvent -> {isDarkTheme = !isDarkTheme; updateTheme(); } );

    }

    void updateTheme() {
        if(isDarkTheme) {
            scene.getStylesheets().clear();
            scene.getStylesheets().add("/DarkTheme.css");
        } else {
            scene.getStylesheets().clear();
            scene.getStylesheets().add("/WhiteTheme.css");
        }

    }
}
