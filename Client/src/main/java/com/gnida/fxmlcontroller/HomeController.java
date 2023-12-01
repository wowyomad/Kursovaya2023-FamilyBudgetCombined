package com.gnida.fxmlcontroller;

import com.gnida.SceneManager;
import com.gnida.model.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;


public class HomeController {

    @FXML
    private Button exitButton;

    @FXML Button button1;

    @FXML Button button2;

    @FXML
    void onBackClicked(ActionEvent event) {
        Scene scene = exitButton.getScene();
        SceneManager.getPreviousRoot(scene);
    }

    @FXML
    void initialize() {
        button1.setOnAction((it) -> {
            System.out.println("button1 clcked");
            Request request = Request.builder()
                    .type(Request.RequestType.GET)
                    .endPoint("/booba")
                    .route(Request.Route.USER)
                    .build();

        });
        button2.setOnAction((it) -> {
            System.out.println("button2 clicked");
        });
    }

}
