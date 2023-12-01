package com.gnida.fxmlcontroller;

import com.gnida.Client;
import com.gnida.Main;
import com.gnida.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public abstract class GenericController {

    protected Scene scene;

    protected final Client client;

    @FXML
    protected Button backButton;

    @FXML
    protected abstract void initialize();

    public GenericController() {
        client = Main.getContext().getBean("client", Client.class);
    }
    public final void setSceneReference(Scene scene) {
        this.scene = scene;
    }

    protected void onBackButtonClick() {
        SceneManager.getPreviousRoot(scene);
    }



}