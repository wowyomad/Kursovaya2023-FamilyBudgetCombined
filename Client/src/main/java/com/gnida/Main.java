package com.gnida;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gnida.converter.Converter;
import com.gnida.domain.UserDto;
import com.gnida.entity.User;
import com.gnida.model.Request;
import com.gnida.model.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

import java.io.*;
import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Main {
    static ApplicationContext context;
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        context = SpringApplication.run(Main.class, args);

        Client client = context.getBean(Client.class);

        User user = new User();
        user.setLogin("root");
        user.setPassword("root");

        String json = Converter.toJson(user);

        TypeReference<List<UserDto>> typeReference = new TypeReference<List<UserDto>>() {
        };

        Request request = Request.builder()
                .type(Request.RequestType.POST)
                .route(Request.Path.USER)
                .endPoint("/login")
                .json(json)
                .build();
        System.out.println(request);
        Response response = client.sendRequest(request);
        System.out.println(response);


    }
}