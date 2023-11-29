package com.gnida.fxmlcontroller;

import com.gnida.Client;
import com.gnida.Main;
import com.gnida.SceneLoader;
import com.gnida.converter.Converter;
import com.gnida.entity.User;
import com.gnida.model.Request;
import com.gnida.model.Response;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.nio.channels.NotYetConnectedException;

public class AuthorizeController {

    private static final String loginRegex = "^[a-zA-Z0-9_]+$";
    private static final String passwordRegex = "^[a-zA-Z\\d@$!%*?&#]+$";

    @FXML
    private Label errorMessage;

    @FXML
    private Button exitButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void onExitClicked() {
        Platform.exit();
    }

    private final Client client;


    public AuthorizeController() {
        client = Main.getContext().getBean(Client.class);

        if (!client.isConnected()) {
            throw new NotYetConnectedException();
        }
    }
    @FXML
    public void onLoginClicked() {
        if (!areLoginAndPasswordCorrect()) {
            showIncorrectCredentialsAlert();
            return;
        }
        User user = new User();
        user.setLogin(loginField.getText());
        user.setPassword(passwordField.getText());
        Request request = Request.builder()
                .type(Request.RequestType.POST)
                .route(Request.Route.USER)
                .endPoint("/login")
                .json(Converter.toJson(user))
                .build();

        Response response = client.sendRequest(request);
        Response.Status status = response.getStatus();
        System.out.println(response);
        if (Response.Status.NOT_FOUND.equals(status)) {
            errorMessage.setText(response.getMessage());
            errorMessage.setVisible(true);
        } else if (Response.Status.NOT_ACTIVE.equals(status)) {
            errorMessage.setText(response.getMessage());
            errorMessage.setVisible(true);
        }
        else {
            User currentUser = Converter.fromJson(response.getJson(), User.class);
            SceneLoader.loadScene(loginButton.getScene(), 
                    switch(currentUser.getRole()) {
                        case USER -> "/user-view.fxml";
                        case ADMIN -> "/admin-view.fxml";
                    });;
        }
    }

    @FXML
    public void onRegisterClicked() {
        if (!areLoginAndPasswordCorrect()) {
            showIncorrectCredentialsAlert();
            return;
        }
        User user = new User();
        user.setLogin(loginField.getText());
        user.setPassword(passwordField.getText());
        Request request = Request.builder()
                .type(Request.RequestType.POST)
                .route(Request.Route.USER)
                .endPoint("/register")
                .json(Converter.toJson(user))
                .build();
        Response response = client.sendRequest(request);
        Response.Status status = response.getStatus();
        if (status == Response.Status.CONFLICT) {
            errorMessage.setText("Введенный вами логин занят");
            errorMessage.setVisible(true);
        } else {
            SceneLoader.loadScene(registerButton.getScene(), "/user-view.fxml");

        }

    }

    private boolean areLoginAndPasswordCorrect() {
        String login = loginField.getText();
        String password = passwordField.getText();
        System.out.println(login);
        System.out.println(password);
        return login.matches(loginRegex) && password.matches(passwordRegex);

    }

    @FXML
    private void hideErrors() {
        errorMessage.setVisible(false);
    }

    private void showIncorrectCredentialsAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Неверный ввод");
        alert.setHeaderText(null);
        alert.setContentText("Введенный логин или пароль содержат недопустмые символы. Попробуйте еще раз.");
        alert.showAndWait();
    }

}
