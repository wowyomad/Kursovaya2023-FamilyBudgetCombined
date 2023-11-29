package com.gnida;

import com.gnida.converter.Converter;
import com.gnida.entity.Budget;
import com.gnida.entity.User;
import com.gnida.entity.UserInfo;
import com.gnida.mappings.Mapping;
import com.gnida.model.Request;
import com.gnida.model.Response;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;


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
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/authorize-view.fxml"));
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }
}