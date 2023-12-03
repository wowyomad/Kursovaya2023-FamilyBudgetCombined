package com.gnida;

import com.gnida.fxmlcontroller.GenericController;
import com.gnida.fxmlcontroller.windows.Theme;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Stack;

public class SceneManager {
    private static Stack<Parent> roots = new Stack<>();

    public static void loadScene(Scene current, String next) {
        FXMLLoader fxmlLoader =new FXMLLoader(Main.class.getResource(next));

        roots.push(current.getRoot());
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        current.setRoot(fxmlLoader.getRoot());
        ((GenericController)fxmlLoader.getController()).setSceneReference(current);

    }
    public static void getPreviousRoot(Scene current) {
        System.out.println("get root");
        if(!roots.isEmpty()) {
            current.setRoot(roots.pop());
        } else  {
            Platform.exit();
        }
    }

    public static void setSceneTheme(Scene scene, String theme_path_css) {
//        scene.getStylesheets().clear();
        scene.getStylesheets().add(Theme.DARK_THEME);
    }
}
