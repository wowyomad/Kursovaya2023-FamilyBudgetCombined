package com.gnida.fxmlcontroller;

import com.gnida.Client;
import com.gnida.Main;
import com.gnida.SceneManager;
import com.gnida.entity.Budget;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public abstract class GenericController {

    protected Scene scene;

    protected SceneManager manager;

    protected final Client client;

    @FXML
    protected Button backButton;

    @FXML
    protected void initialize() {
        backButton.setOnAction(actionEvent -> onBackButtonClick());
        this.scene = SceneManager.getMainScene();
    }

    public GenericController() {
        client = Main.getContext().getBean("client", Client.class);
    }


    @FXML
    protected void onBackButtonClick() {
        SceneManager.getPreviousRoot(scene);
    }


}