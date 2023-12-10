package com.gnida;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Stack;

public class SceneManager {
    @Setter @Getter
    private static Scene mainScene;

    private static final Stack<Parent> roots = new Stack<>();

    public static void loadScene(String next) {
        FXMLLoader fxmlLoader =new FXMLLoader(Main.class.getResource(next));
        try {
            fxmlLoader.load();
            roots.push(mainScene.getRoot());
        } catch (IOException e) {
            System.out.println("No screen loaded");
            e.printStackTrace();
        }
        mainScene.setRoot(fxmlLoader.getRoot());

    }
    public static void getPreviousRoot(Scene current) {
        System.out.println("get root");
        if(!roots.isEmpty()) {
            current.setRoot(roots.pop());
        } else  {
            PrefabDialogs.exitDialog(null);
        }
    }

    public static void setSceneTheme(String theme_path_css) {
        mainScene.getStylesheets().clear();;
        mainScene.getStylesheets().add(theme_path_css);
    }

}
