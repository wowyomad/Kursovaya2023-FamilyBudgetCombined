package com.gnida.fxmlcontroller;

import com.gnida.SceneManager;
import com.gnida.entity.User;
import com.gnida.enums.UserRole;
import com.gnida.fxmlcontroller.windows.Screen;
import com.gnida.fxmlcontroller.windows.Theme;
import com.gnida.model.Request;
import com.gnida.model.Response;
import com.gnida.requests.SuperRequest;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

import java.nio.channels.NotYetConnectedException;

public class AuthorizeController extends GenericController {

    private static final String loginRegex = "^[a-zA-Z0-9_]+$";
    private static final String passwordRegex = "^[a-zA-Z\\d@$!%*?&#]+$";

    @FXML
    private Label errorMessage;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @Override
    protected void initialize() {
        super.initialize();
    }

    public AuthorizeController() {
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
        Request request = SuperRequest.POST_USER_LOGIN.object(user).build();
        Response response = client.sendRequest(request);

        if (Response.Status.OK.equals(response.getStatus())) {
            User currentUser = (User) response.getObject();
            Scene scene = loginButton.getScene();
            SceneManager.loadScene(switch (currentUser.getRole()) {
                        case USER -> Screen.HOME_USER;
                        case ADMIN -> Screen.HOME_ADMIN;
                    });
        } else {
            showErrorMessage(response.getMessage());
        }
    }

    private void showErrorMessage(String message) {
        errorMessage.setStyle("-fx-text-fill: red; -fx-background-color: transparent");
        errorMessage.setText(message);
        errorMessage.setVisible(true);
    }

    @FXML
    public void onRegisterClicked() {
        if (!areLoginAndPasswordCorrect()) {
            showIncorrectCredentialsAlert();
            return;
        }
        User user = User.builder().login(loginField.getText()).password(passwordField.getText()).build();
        Request request = SuperRequest.POST_USER_REGISTER.object(user).build();
        Response response = client.sendRequest(request);
        Response.Status status = response.getStatus();
        if (status == Response.Status.CONFLICT) {
            errorMessage.setText("Введенный вами логин занят");
            errorMessage.setVisible(true);
        } else {
            UserRole role = ((User) response.getObject()).getRole();
            Scene scene = registerButton.getScene();
            SceneManager.loadScene(switch (role) {
                case USER -> Screen.HOME_USER;
                case ADMIN -> Screen.HOME_ADMIN;
            });
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
        alert.setContentText("Введенный логин или пароль содержат недопустимые символы. Попробуйте еще раз.");
        alert.showAndWait();
    }

}
