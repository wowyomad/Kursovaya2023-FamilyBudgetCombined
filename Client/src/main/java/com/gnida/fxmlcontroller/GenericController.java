package com.gnida.fxmlcontroller;

import javafx.scene.Scene;

public abstract class GenericController {

    protected Scene scene;
    public final void setSceneReference(Scene scene) {
        this.scene = scene;
    }

}