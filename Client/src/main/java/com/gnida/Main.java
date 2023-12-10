package com.gnida;

import com.gnida.fxmlcontroller.windows.Screen;
import com.gnida.fxmlcontroller.windows.Theme;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

import java.io.IOException;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Main extends Application {
    @Getter
    private static ApplicationContext context;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        context = SpringApplication.run(Main.class, args);
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(Screen.AUTHORIZE));
        fxmlLoader.load();
        Scene scene = new Scene(fxmlLoader.getRoot());
        SceneManager.setMainScene(scene);
        SceneManager.setSceneTheme(Theme.DARK_THEME);
        stage.setTitle("Здарова");
        stage.getIcons().add(new Image("icon/isaac_256x256.png"));
        stage.setScene(scene);
        stage.setOnCloseRequest(PrefabDialogs::exitDialog);
        stage.show();
    }
}