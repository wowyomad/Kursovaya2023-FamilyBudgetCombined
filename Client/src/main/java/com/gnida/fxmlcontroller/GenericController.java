package com.gnida.fxmlcontroller;

import com.gnida.SceneManager;
import javafx.scene.Scene;

public abstract class GenericController {

    protected Scene scene;
    public final void setSceneReference(Scene scene) {
        this.scene = scene;
    }

    protected void onBackButtonClick() {
        SceneManager.getPreviousRoot(scene);
    }

}