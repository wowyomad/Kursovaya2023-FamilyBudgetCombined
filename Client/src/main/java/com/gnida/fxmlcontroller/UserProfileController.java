package com.gnida.fxmlcontroller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UserProfileController extends GenericController {
    @FXML
    Button backButton;

    @Override
    protected void initialize() {
        backButton.setOnAction(actionEvent -> onBackButtonClick());

    }
}
