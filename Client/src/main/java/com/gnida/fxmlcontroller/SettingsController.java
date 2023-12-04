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
        SceneManager.setSceneTheme(switch(Boolean.hashCode(isDarkTheme)) {
            case 1231 -> Theme.WHITE_THEME;
            case 1237 -> Theme.DARK_THEME;
            default -> throw new RuntimeException("oh no");
        });

//        SceneManager.setSceneTheme(isDarkTheme ? Theme.DARK_THEME : Theme.WHITE_THEME);

    }
}
