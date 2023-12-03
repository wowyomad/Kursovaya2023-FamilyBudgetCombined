package com.gnida.fxmlcontroller;

import com.gnida.SceneManager;
import com.gnida.fxmlcontroller.windows.Theme;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SettingsController extends GenericController {

    @FXML
    public Button switchThemeButton;

    private boolean isDarkTheme = false;

    @Override
    protected void initialize() {
        super.initialize();

        switchThemeButton.setOnAction(actionEvent -> {
            isDarkTheme = !isDarkTheme;
            updateTheme();
        });

    }

    void updateTheme() {
        if (isDarkTheme) {
            SceneManager.setSceneTheme(scene, Theme.DARK_THEME);
        } else {
            SceneManager.setSceneTheme(scene, Theme.WHITE_THEME);
        }

    }
}
