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

        Client client = context.getBean(Client.class);

        User registerUser = new User();
        registerUser.setPassword("root");
        registerUser.setLogin("root");
        Request req = Request.builder()
                .type(Request.RequestType.POST)
                .route(Request.Route.USER)
                .endPoint("/register")
                .json(Converter.toJson(registerUser))
                .build();
        req.setEndPoint("/login");
        User currentUser = null;
        Response loginResponse = client.sendRequest(req);
        System.out.println(loginResponse);
        if(loginResponse.getStatus().equals(Response.Status.OK)) {
            currentUser = Converter.fromJson(loginResponse.getJson(),User.class);
        }
        else {
            req.setEndPoint("/register");
            Response registerResponse = client.sendRequest(req);
            System.out.println(registerResponse);
        }

        UserInfo info = new UserInfo();
        info.setFirstName("Vadim");
        info.setSecondName("Sundukov");
        Response setInfoResponse = client.sendRequest(Request.builder()
                .route(Request.Route.USER)
                .endPoint(Mapping.User.info)
                .type(Request.RequestType.UPDATE)
                .json(Converter.toJson(info))
                .build());
        System.out.println(setInfoResponse);


        Budget budget = new Budget();
        budget.setName("Деньга на месяц");
        budget.setExpectedIncome(new BigDecimal(5000));
        budget.setExpectedExpense(new BigDecimal(2500));
        budget.setInitialAmount(new BigDecimal(8000));

        Request createBudgetRequest = Request.builder()
                .type(Request.RequestType.POST)
                .route(Request.Route.BUDGET)
                .endPoint("/add")
                .json(Converter.toJson(budget))
                .build();

        System.out.println(createBudgetRequest);

        Response budgetResponse = client.sendRequest(createBudgetRequest);

        System.out.println(budgetResponse);

//        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/authorize-view.fxml"));
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }
}