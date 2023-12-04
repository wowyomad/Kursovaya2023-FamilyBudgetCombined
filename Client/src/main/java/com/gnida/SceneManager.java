package com.gnida;

import com.gnida.fxmlcontroller.GenericController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.support.ScopeNotActiveException;

import java.io.IOException;
import java.util.Stack;

public class SceneManager {
    @Setter @Getter
    private static Scene mainScene;

    private static final Stack<Parent> roots = new Stack<>();

    public static void loadScene(String next) {
        FXMLLoader fxmlLoader =new FXMLLoader(Main.class.getResource(next));

        roots.push(mainScene.getRoot());
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        mainScene.setRoot(fxmlLoader.getRoot());

    }
    public static void getPreviousRoot(Scene current) {
        System.out.println("get root");
        if(!roots.isEmpty()) {
            current.setRoot(roots.pop());
        } else  {
            Platform.exit();
        }
    }

    public static void setSceneTheme(String theme_path_css) {
        mainScene.getStylesheets().clear();;
        mainScene.getStylesheets().add(theme_path_css);
    }

}
