package com.gnida;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Stack;

public class SceneLoader {

    private static Stack<Parent> roots = new Stack<>();
    public static void loadScene(Scene current, String next) {

        FXMLLoader fxmlLoader =new FXMLLoader(Main.class.getResource("/home-view.fxml"));

        roots.push(current.getRoot());
        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        current.setRoot(fxmlLoader.getRoot());
    }

    public static void getPreviousRoot(Scene current) {
        if(!roots.isEmpty()) {
            current.setRoot(roots.pop());
        }
    }
}
